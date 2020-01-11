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
        int packageFlg = 0;
        int descFlg = 0;

        List<BioProjectBean> bioProjectBeanList = new ArrayList<>();
        BioProjectBean bioProjectBean = null;

        for (; reader.hasNext(); reader.next()) {
            int eventType = reader.getEventType();

            if (packageFlg == 0
            && eventType == XMLStreamConstants.START_ELEMENT
            && reader.getName().toString().equals("Package")) {
                packageFlg = 1;
                bioProjectBean = new BioProjectBean();
            } else if (packageFlg == 1
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("ArchiveID")) {
                bioProjectBean.setIdentifier(parseArchiveID(reader));
            } else if (packageFlg == 1
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("ProjectDescr")) {
                descFlg = 1;
            } else if (descFlg == 1
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("Name")) {
                bioProjectBean.setName(reader.getElementText());
            } else if (descFlg == 1
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("Title")) {
                bioProjectBean.setTitle(reader.getElementText());
            } else if (descFlg == 1
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("Description")) {
                bioProjectBean.setDescription(reader.getElementText());
                descFlg = 0;
            } else if (packageFlg == 1
                    && eventType == XMLStreamConstants.END_ELEMENT
                    && reader.getName().toString().equals("Package")) {
                packageFlg = 0;
                bioProjectBeanList.add(bioProjectBean);
            }
        }

        reader.close();

        return bioProjectBeanList;
    }

    private static String parseArchiveID(XMLStreamReader reader) {

        String accession = null;
        int count = reader.getAttributeCount();
        for (int i=0; i<count; i++) {
            if (reader.getAttributeName(i).toString().equals("accession")) {
                accession = reader.getAttributeValue(i);
                break;
            }
        }

        return accession;
    }
}
