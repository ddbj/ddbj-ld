package com.ddbj.ld.parser.common;

import com.ddbj.ld.common.annotation.Parser;
import lombok.extern.slf4j.Slf4j;

import javax.xml.stream.XMLStreamReader;

@Parser
@Slf4j
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

        log.debug("accession:" + accession);

        return accession;
    }
}
