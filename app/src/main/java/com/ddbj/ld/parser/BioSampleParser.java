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
    private Settings settings;

    public List<BioSampleBean> parse(String xmlFile) {
        XMLStreamReader reader = null;

        // Debug用
        String mode = settings.getMode();
        int recordLimit = settings.getDevelopmentRecordNumber();
        int recordCnt = 0;

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
            reader = factory.createXMLStreamReader(stream);

            boolean isStarted = false;
            boolean isDescription = false;
            BioSampleBean bioSampleBean = null;
            List<BioSampleBean> bioSampleBeanList = new ArrayList<>();

            // TODO description
            for (; reader.hasNext(); reader.next()) {
                int eventType = reader.getEventType();

                if (isStarted == false
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("BioSample")) {

                    // Debug用
                    if(mode.equals("Development")
                            && recordLimit == recordCnt
                    ) {
                        break;
                    }

                    isStarted = true;
                    bioSampleBean = new BioSampleBean();
                    bioSampleBean.setIdentifier(accessionParser.parseAccession(reader));
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("Description")) {
                    isDescription = true;
                } else if (isDescription == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("SampleName")) {
                    bioSampleBean.setName(reader.getElementText());
                } else if (isDescription == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("Title")) {
                    bioSampleBean.setTitle(reader.getElementText());
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.END_ELEMENT
                        && reader.getName().toString().equals("BioSample")) {
                    isStarted = false;
                    isDescription = false;
                    bioSampleBeanList.add(bioSampleBean);

                    recordCnt++;
                }
            }

            return bioSampleBeanList;
        } catch (FileNotFoundException | XMLStreamException e) {
            log.debug(e.getMessage());

            return null;
        } finally {
            try {
                reader.close();
            } catch (XMLStreamException e) {

            }
        }
    }
}
