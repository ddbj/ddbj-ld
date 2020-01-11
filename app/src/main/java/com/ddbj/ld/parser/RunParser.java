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

import com.ddbj.ld.bean.RunBean;

public class RunParser {
    public static List<RunBean> parse(String xmlFile) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
        XMLStreamReader reader = factory.createXMLStreamReader(stream);

        boolean isStarted = false;
        RunBean runBean = null;
        List<RunBean> runBeanList = new ArrayList<>();

        // TODO nameとdescription
        for (; reader.hasNext(); reader.next()) {
            int eventType = reader.getEventType();

            if (isStarted == false
            && eventType == XMLStreamConstants.START_ELEMENT
            && reader.getName().toString().equals("RUN")) {
                isStarted = true;
                runBean = new RunBean();
                runBean.setIdentifier(AccessionParser.parseAccession(reader));
            } else if (isStarted == true
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("TITLE")) {
                runBean.setTitle(reader.getElementText());
            } else if (isStarted == true
                    && eventType == XMLStreamConstants.END_ELEMENT
                    && reader.getName().toString().equals("RUN")) {
                isStarted = false;
                runBeanList.add(runBean);
            }
        }

        reader.close();

        return runBeanList;
    }
}
