package com.ddbj.ld.parser;

import com.ddbj.ld.bean.SampleBean;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SampleParser {
    public static List<SampleBean> parse(String xmlFile) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
        XMLStreamReader reader = factory.createXMLStreamReader(stream);

        boolean isStarted = false;
        SampleBean sampleBean = null;
        List<SampleBean> sampleBeanList = new ArrayList<>();

        // TODO name
        for (; reader.hasNext(); reader.next()) {
            int eventType = reader.getEventType();

            if (isStarted == false
            && eventType == XMLStreamConstants.START_ELEMENT
            && reader.getName().toString().equals("SAMPLE")) {
                isStarted = true;
                sampleBean = new SampleBean();
                sampleBean.setIdentifier(AccessionParser.parseAccession(reader));
            } else if (isStarted == true
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("TITLE")) {
                sampleBean.setTitle(reader.getElementText());
            } else if (isStarted == true
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("Description")) {
                sampleBean.setDescription(reader.getElementText());
            } else if (isStarted == true
                    && eventType == XMLStreamConstants.END_ELEMENT
                    && reader.getName().toString().equals("SAMPLE")) {
                isStarted = false;
                sampleBeanList.add(sampleBean);
            }
        }

        reader.close();

        return sampleBeanList;
    }
}
