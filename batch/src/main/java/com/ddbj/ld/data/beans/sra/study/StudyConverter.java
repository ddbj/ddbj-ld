package com.ddbj.ld.data.beans.sra.study;

import com.ddbj.ld.data.beans.common.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

public class StudyConverter {

    public static final ObjectMapper mapper = Converter.getMapper();
    public static final ObjectReader reader = mapper.readerFor(Study.class);
    public static final ObjectWriter writer = mapper.writerFor(Study.class);

    public static Study fromJsonString(
            final String json
    ) throws IOException {
        return reader.readValue(json);
    }

    public static String toJsonString(
            final Study obj
    ) throws JsonProcessingException {
        return writer.writeValueAsString(obj);
    }
}
