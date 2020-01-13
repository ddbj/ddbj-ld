package com.ddbj.ld.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class JsonParser {
    public List<String> parse (List<?> beanList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            List<String> jsonList = new ArrayList<>();

            for (Object bean : beanList) {
                String json = objectMapper.writeValueAsString(bean);
                jsonList.add(json);
            }

            return jsonList;
        } catch (IOException e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
