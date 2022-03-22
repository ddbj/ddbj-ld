package com.ddbj.ld.data.beans.jga.policy;

import com.ddbj.ld.data.beans.common.Converter;
import com.ddbj.ld.data.beans.jga.dataset.Dataset;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

public class PolicyConverter {

    public static final ObjectMapper mapper = Converter.getMapper();
    public static final ObjectReader reader = mapper.readerFor(Policy.class);
    public static final ObjectWriter writer = mapper.writerFor(Policy.class);

    public static Policy fromJsonString(
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
