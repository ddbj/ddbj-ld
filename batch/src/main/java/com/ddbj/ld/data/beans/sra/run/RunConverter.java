package com.ddbj.ld.data.beans.sra.run;

import com.ddbj.ld.data.beans.common.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

public class RunConverter {

    public static final ObjectMapper mapper = Converter.getMapper();
    public static final ObjectReader reader = mapper.readerFor(Run.class);
    public static final ObjectWriter writer = mapper.writerFor(Run.class);

    public static Run fromJsonString(
            final String json
    ) throws IOException {
        return reader.readValue(json);
    }

    public static String toJsonString(
            final Run obj
    ) throws JsonProcessingException {
        return writer.writeValueAsString(obj);
    }
}
