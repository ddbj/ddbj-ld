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

import com.ddbj.ld.bean.BioSampleBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BioSampleParser {
    private AccessionParser accessionParser;

    public List<BioSampleBean> parse(String xmlFile) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
        XMLStreamReader reader = factory.createXMLStreamReader(stream);

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
            }
        }

        reader.close();

        return bioSampleBeanList;
    }
}
