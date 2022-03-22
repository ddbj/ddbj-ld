package com.ddbj.ld.data.beans.jga.dac;

import com.ddbj.ld.data.beans.common.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class DacConverter {

    public static final ObjectMapper mapper = Converter.getMapper();
    public static final ObjectReader reader = mapper.readerFor(DAC.class);
    public static final ObjectWriter writer = mapper.writerFor(DAC.class);

    public static DAC fromJsonString(
            final String json
    ) throws IOException {
        return reader.readValue(json);
    }

    public static String toJsonString(
            final DAC obj
    ) throws JsonProcessingException {
        return writer.writeValueAsString(obj);
    }
}
