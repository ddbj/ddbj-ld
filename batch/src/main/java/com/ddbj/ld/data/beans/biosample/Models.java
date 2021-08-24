package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Models {
    private List<Model> model;

    @JsonProperty("Model")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Models.ModelDeserializer.class)
    public List<Model> getModel() { return model; }
    @JsonProperty("Model")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Models.ModelDeserializer.class)
    public void setModel(List<Model> value) { this.model = value; }

    static class ModelDeserializer extends JsonDeserializer<List<Model>> {
        @Override
        public List<Model> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Model>();
            var mapper = Converter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    jsonParser.readValueAs(String.class);
//                    values.add("");
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Model>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Model.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Models.ModelDeserializer");
            }
            return values;
        }
    }
}
