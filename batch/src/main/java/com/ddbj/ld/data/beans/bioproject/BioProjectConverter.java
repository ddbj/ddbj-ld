package com.ddbj.ld.data.beans.bioproject;

import com.ddbj.ld.data.beans.common.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

public class BioProjectConverter {

    public static final ObjectMapper mapper = Converter.getMapper();
    public static final ObjectReader reader = mapper.readerFor(BioProject.class);
    public static final ObjectWriter writer = mapper.writerFor(BioProject.class);

    public static BioProject fromJsonString(
            final String json
    ) throws IOException {
        return reader.readValue(json);
    }

    public static String toJsonString(
            final BioProject obj
    ) throws JsonProcessingException {
        return writer.writeValueAsString(obj);
    }
}
