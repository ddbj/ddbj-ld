package com.ddbj.ld.parser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.ddbj.ld.common.ParserHelper;
import com.ddbj.ld.common.Settings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ddbj.ld.bean.BioSampleBean;

@Component
@AllArgsConstructor
@Slf4j
public class BioSampleParser {
    private AccessionParser accessionParser;
    private ParserHelper parserHelper;

    public BioSampleBean parse(XMLStreamReader bioSampleReader) {
        try {
            boolean isDescription = false;
            BioSampleBean bioSampleBean = new BioSampleBean();

            for (; bioSampleReader.hasNext(); bioSampleReader.next()) {
                int eventType = bioSampleReader.getEventType();

                if (eventType == XMLStreamConstants.START_ELEMENT
                        && bioSampleReader.getName().toString().equals("BioSample")) {
                    bioSampleBean.setIdentifier(accessionParser.parseAccession(bioSampleReader));
                } else if (eventType == XMLStreamConstants.START_ELEMENT
                        && bioSampleReader.getName().toString().equals("Description")) {
                    isDescription = true;
                } else if (isDescription == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && bioSampleReader.getName().toString().equals("SampleName")) {
                    bioSampleBean.setName(parserHelper.getElementText((bioSampleReader)));
                } else if (isDescription == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && bioSampleReader.getName().toString().equals("Title")) {
                    bioSampleBean.setTitle(parserHelper.getElementText((bioSampleReader)));
                } else if (eventType == XMLStreamConstants.END_ELEMENT
                        && bioSampleReader.getName().toString().equals("BioSample")) {
                    break;
                }
            }

            return bioSampleBean;
        } catch (XMLStreamException e) {
            log.debug(e.getMessage());

            return null;
        }
    }
}
