package com.ddbj.ld.data.beans.sra.sample;

import com.ddbj.ld.data.beans.common.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

public class SampleConverter {

    public static final ObjectMapper mapper = Converter.getMapper();
    public static final ObjectReader reader = mapper.readerFor(Sample.class);
    public static final ObjectWriter writer = mapper.writerFor(Sample.class);

    public static Sample fromJsonString(
            final String json
    ) throws IOException {
        return reader.readValue(json);
    }

    public static String toJsonString(
            final Sample obj
    ) throws JsonProcessingException {
        return writer.writeValueAsString(obj);
    }
}
