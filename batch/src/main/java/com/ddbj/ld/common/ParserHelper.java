package com.ddbj.ld.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

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

    public String readAll(final String path) throws IOException {
        return Files.lines(Paths.get(path), Charset.forName("UTF-8"))
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }
}
