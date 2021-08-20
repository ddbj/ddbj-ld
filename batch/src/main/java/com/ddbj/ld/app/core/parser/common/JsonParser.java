package com.ddbj.ld.app.core.parser.common;

import com.ddbj.ld.common.annotation.Parser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Parser
@AllArgsConstructor
@Slf4j
@Deprecated
// FIXME Beanに入れたSingletonを使う方向にする
public class JsonParser {
    private ObjectMapper objectMapper;

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

    public String parse(Object bean) {
        try {
            return objectMapper.writeValueAsString(bean);

        } catch (IOException e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
