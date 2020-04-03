package com.ddbj.ld.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class JsonParser {
    public String parse (Object bean, ObjectMapper mapper) {
        try {
            return mapper.writeValueAsString(bean);
        } catch (IOException e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    public ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return mapper;
    }
}
