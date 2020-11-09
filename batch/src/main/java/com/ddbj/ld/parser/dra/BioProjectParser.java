package com.ddbj.ld.parser.dra;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;

import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.parser.common.AccessionParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ddbj.ld.bean.dra.BioProjectBean;

@Component
@AllArgsConstructor
@Slf4j
public class BioProjectParser {
    private AccessionParser accessionParser;
    private ParserHelper parserHelper;

    public List<BioProjectBean> parse(String xmlFile) {
        XMLStreamReader reader = null;

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
            reader = factory.createXMLStreamReader(stream);

            boolean isStarted     = false;
            boolean isDescription = false;

            List<BioProjectBean> bioProjectBeanList = new ArrayList<>();
            BioProjectBean bioProjectBean = null;

            for (; reader.hasNext(); reader.next()) {
                int eventType = reader.getEventType();

                if (isStarted == false
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("Package")) {
                    isStarted = true;
                    bioProjectBean = new BioProjectBean();
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("ArchiveID")) {
                    bioProjectBean.setIdentifier(accessionParser.parseAccession(reader));
                    log.debug("parser:" + bioProjectBean.getIdentifier());
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("ProjectDescr")) {
                    isDescription = true;
                } else if (isDescription == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("Name")) {
                    bioProjectBean.setName(parserHelper.getElementText((reader)));
                } else if (isDescription == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("Title")) {
                    bioProjectBean.setTitle(parserHelper.getElementText((reader)));
                } else if (isDescription == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("Description")) {
                    bioProjectBean.setDescription(parserHelper.getElementText((reader)));
                    isDescription = false;
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.END_ELEMENT
                        && reader.getName().toString().equals("Package")) {
                    isStarted = false;
                    isDescription = false;
                    bioProjectBeanList.add(bioProjectBean);
                }
            }

            return bioProjectBeanList;

        } catch (FileNotFoundException | XMLStreamException e) {
            log.debug(e.getMessage());

            return null;
        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (XMLStreamException e ) {
                log.debug(e.getMessage());
            }
        }
    }
}