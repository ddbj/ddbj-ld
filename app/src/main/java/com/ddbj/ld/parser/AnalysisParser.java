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

import com.ddbj.ld.bean.AnalysisBean;

public class AnalysisParser {
    public static List<AnalysisBean> parse(String xmlFile) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
        XMLStreamReader reader = factory.createXMLStreamReader(stream);

        boolean isStarted = false;
        AnalysisBean analysisBean = null;
        List<AnalysisBean> analysisBeanList = new ArrayList<>();

        // TODO name
        for (; reader.hasNext(); reader.next()) {
            int eventType = reader.getEventType();

            if (isStarted == false
            && eventType == XMLStreamConstants.START_ELEMENT
            && reader.getName().toString().equals("SAMPLE")) {
                isStarted = true;
                analysisBean = new AnalysisBean();
                analysisBean.setIdentifier(AccessionParser.parseAccession(reader));
            } else if (isStarted == true
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("TITLE")) {
                analysisBean.setTitle(reader.getElementText());
            } else if (isStarted == true
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("Description")) {
                analysisBean.setDescription(reader.getElementText());
            } else if (isStarted == true
                    && eventType == XMLStreamConstants.END_ELEMENT
                    && reader.getName().toString().equals("SAMPLE")) {
                isStarted = false;
                analysisBeanList.add(analysisBean);
            }
        }

        reader.close();

        return analysisBeanList;
    }
}
