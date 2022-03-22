package com.ddbj.ld.data.beans.biosample;

import com.ddbj.ld.data.beans.common.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

public class BioSampleConverter {

    public static final ObjectMapper mapper = Converter.getMapper();
    public static final ObjectReader reader = mapper.readerFor(BioSample.class);
    public static final ObjectWriter writer = mapper.writerFor(BioSample.class);

    public static BioSample fromJsonString(
            final String json
    ) throws IOException {
        return reader.readValue(json);
    }

    public static String toJsonString(
            final BioSample obj
    ) throws JsonProcessingException {
        return writer.writeValueAsString(obj);
    }
}
