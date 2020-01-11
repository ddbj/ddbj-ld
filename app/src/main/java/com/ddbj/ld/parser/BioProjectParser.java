package com.ddbj.ld.parser;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;

import com.ddbj.ld.bean.BioProjectBean;

public class BioProjectParser {
    public static List<BioProjectBean> parse(String xmlFile) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
        XMLStreamReader reader = factory.createXMLStreamReader(stream);
        boolean isStarted = false;
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
                bioProjectBean.setIdentifier(AccessionParser.parseAccession(reader));
            } else if (isStarted == true
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("ProjectDescr")) {
                isDescription = true;
            } else if (isDescription == true
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("Name")) {
                bioProjectBean.setName(reader.getElementText());
            } else if (isDescription == true
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("Title")) {
                bioProjectBean.setTitle(reader.getElementText());
            } else if (isDescription == true
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("Description")) {
                bioProjectBean.setDescription(reader.getElementText());
                isDescription = false;
            } else if (isStarted == true
                    && eventType == XMLStreamConstants.END_ELEMENT
                    && reader.getName().toString().equals("Package")) {
                isStarted = false;
                isDescription = false;
                bioProjectBeanList.add(bioProjectBean);
            }
        }

        reader.close();

        return bioProjectBeanList;
    }
}
