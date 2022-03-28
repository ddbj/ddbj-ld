package com.ddbj.ld.data.beans.sra.experiment;

import com.ddbj.ld.data.beans.common.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

public class ExperimentConverter {

    public static final ObjectMapper mapper = Converter.getMapper();
    public static final ObjectReader reader = mapper.readerFor(Experiment.class);
    public static final ObjectWriter writer = mapper.writerFor(Experiment.class);

    public static Experiment fromJsonString(
            final String json
    ) throws IOException {
        return reader.readValue(json);
    }

    public static String toJsonString(
            final Experiment obj
    ) throws JsonProcessingException {
        return writer.writeValueAsString(obj);
    }
}
