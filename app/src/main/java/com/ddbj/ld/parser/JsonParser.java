package com.ddbj.ld.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class JsonParser {
    public String parser (Object bean) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            return objectMapper.writeValueAsString(bean);
        } catch (IOException e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
