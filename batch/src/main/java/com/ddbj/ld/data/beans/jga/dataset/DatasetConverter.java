package com.ddbj.ld.data.beans.jga.dataset;

import com.ddbj.ld.data.beans.common.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class DatasetConverter {

    public static final ObjectMapper mapper = Converter.getMapper();
    public static final ObjectReader reader = mapper.readerFor(Dataset.class);
    public static final ObjectWriter writer = mapper.writerFor(Dataset.class);

    public static Dataset fromJsonString(
            final String json
    ) throws IOException {
        return reader.readValue(json);
    }

    public static String toJsonString(
            final Dataset obj
    ) throws JsonProcessingException {
        return writer.writeValueAsString(obj);
    }
}
