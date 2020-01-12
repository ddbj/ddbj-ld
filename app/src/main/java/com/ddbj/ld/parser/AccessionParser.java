package com.ddbj.ld.parser;

import org.springframework.stereotype.Component;
import javax.xml.stream.XMLStreamReader;

@Component
public class AccessionParser {
    public String parseAccession(XMLStreamReader reader) {

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
