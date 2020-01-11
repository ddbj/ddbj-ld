package com.ddbj.ld.parser;

import javax.xml.stream.XMLStreamReader;

public class AccessionParser {
    public static String parseAccession(XMLStreamReader reader) {

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
