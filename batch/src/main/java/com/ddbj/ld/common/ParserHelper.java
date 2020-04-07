package com.ddbj.ld.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

@Slf4j
@Component
public class ParserHelper {
    public String getElementText(XMLStreamReader reader) {
        String elementText = null;

      try {
          elementText = reader.getElementText();
          reader.getEncoding();

          return elementText;
      } catch(XMLStreamException e) {
          return null;
      }
    }
}
