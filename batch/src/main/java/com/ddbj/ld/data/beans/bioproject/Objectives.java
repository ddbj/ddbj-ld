package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Objectives {
    private List<ObjectivesData> data;

    @JsonProperty("Data")
    @JsonDeserialize(using = Objectives.ObjectivesDataDeserializer.class)
    public List<ObjectivesData> getData() { return data; }
    @JsonProperty("Data")
    @JsonDeserialize(using = Objectives.ObjectivesDataDeserializer.class)
    public void setData(List<ObjectivesData> value) { this.data = value; }

    static class ObjectivesDataDeserializer extends JsonDeserializer<List<ObjectivesData>> {
        @Override
        public List<ObjectivesData> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<ObjectivesData> values = new ArrayList<>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list= Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<ObjectivesData>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value= Converter.getObjectMapper().readValue(jsonParser, ObjectivesData.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ObjectivesData");
            }
            return values;
        }
    }
}
