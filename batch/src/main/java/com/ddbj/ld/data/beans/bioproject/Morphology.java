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
public class Morphology {
    private String gram;
    private String enveloped;
    private List<String> shape;
    private String endospores;
    private String motility;

    @JsonProperty("Gram")
    public String getGram() { return gram; }
    @JsonProperty("Gram")
    public void setGram(String value) { this.gram = value; }

    @JsonProperty("Enveloped")
    public String getEnveloped() { return enveloped; }
    @JsonProperty("Enveloped")
    public void setEnveloped(String value) { this.enveloped = value; }

    @JsonProperty("Shape")
    @JsonDeserialize(using = Morphology.ShapeDeserializer.class)
    public List<String> getShape() { return shape; }
    @JsonProperty("Shape")
    @JsonDeserialize(using = Morphology.ShapeDeserializer.class)
    public void setShape(List<String> value) { this.shape = value; }

    @JsonProperty("Endospores")
    public String getEndospores() { return endospores; }
    @JsonProperty("Endospores")
    public void setEndospores(String value) { this.endospores = value; }

    @JsonProperty("Motility")
    public String getMotility() { return motility; }
    @JsonProperty("Motility")
    public void setMotility(String value) { this.motility = value; }

    static class ShapeDeserializer extends JsonDeserializer<List<String>> {
        @Override
        public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values  = new ArrayList<String>();

            switch (jsonParser.currentToken()) {
                case START_ARRAY:
                    var list = Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<String>>() {});
                    values.addAll(list);

                    break;
                case VALUE_STRING:
                    values.add(jsonParser.readValueAs(String.class));

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Shape");
            }
            return values;
        }
    }
}
