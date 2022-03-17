package com.ddbj.ld.data.beans.sra.submission;

import com.ddbj.ld.data.beans.common.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

public class SubmissionConverter {

    public static final ObjectMapper mapper = Converter.getMapper();
    public static final ObjectReader reader = mapper.readerFor(Submission.class);
    public static final ObjectWriter writer = mapper.writerFor(Submission.class);

    public static Submission fromJsonString(
            final String json
    ) throws IOException {
        return reader.readValue(json);
    }

    public static String toJsonString(
            final Submission obj
    ) throws JsonProcessingException {
        return writer.writeValueAsString(obj);
    }
}
