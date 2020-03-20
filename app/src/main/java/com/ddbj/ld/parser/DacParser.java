package com.ddbj.ld.parser;

import com.ddbj.ld.bean.DacBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class DacParser {
    private AccessionParser accessionParser;

    public List<DacBean> parse(String xmlFile) {
        XMLStreamReader reader = null;

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
            reader = factory.createXMLStreamReader(stream);

            boolean isStarted         = false;
            DacBean dacBean           = null;
            List<DacBean> dacBeanList = new ArrayList<>();

            // TODO name, title, description
            for (; reader.hasNext(); reader.next()) {
                int eventType = reader.getEventType();

                if (isStarted == false
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("DAC")) {
                    isStarted = true;
                    dacBean = new DacBean();
                    dacBean.setIdentifier(accessionParser.parseAccession(reader));
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.END_ELEMENT
                        && reader.getName().toString().equals("DAC")) {
                    isStarted = false;
                    dacBeanList.add(dacBean);
                }
            }

            return dacBeanList;
        } catch (FileNotFoundException | XMLStreamException e) {
            log.debug(e.getMessage());

            return null;
        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (XMLStreamException e) {
                log.debug(e.getMessage());
            }
        }
    }
}
