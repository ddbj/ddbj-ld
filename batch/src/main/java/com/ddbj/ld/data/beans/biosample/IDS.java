package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class IDS {
    private List<SampleId> id;

    @JsonProperty("Id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = IDS.SampleIdDeserializer.class)
    public List<SampleId> getID() { return id; }
    @JsonProperty("Id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = IDS.SampleIdDeserializer.class)
    public void setID(List<SampleId> value) { this.id = value; }

    static class SampleIdDeserializer extends JsonDeserializer<List<SampleId>> {
        @Override
        public List<SampleId> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<SampleId> values = new ArrayList<>();
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<SampleId>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, SampleId.class);
                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize IDS.SampleIdDeserializer");
            }
            return values;
        }
    }
}
