package com.ddbj.ld.data.beans.sra.analysis;

import com.ddbj.ld.data.beans.common.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

public class AnalysisConverter {

    public static final ObjectMapper mapper = Converter.getMapper();
    public static final ObjectReader reader = mapper.readerFor(Analysis.class);
    public static final ObjectWriter writer = mapper.writerFor(Analysis.class);

    public static Analysis fromJsonString(
            final String json
    ) throws IOException {
        return reader.readValue(json);
    }

    public static String toJsonString(
            final Analysis obj
    ) throws JsonProcessingException {
        return writer.writeValueAsString(obj);
    }
}
