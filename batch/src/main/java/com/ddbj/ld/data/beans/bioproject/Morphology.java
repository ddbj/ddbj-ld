package com.ddbj.ld.data.beans.bioproject;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonProperty;
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
=======
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

>>>>>>> 取り込み、修正
public class Morphology {
    private String gram;
    private String enveloped;
    private List<String> shape;
    private String endospores;
    private String motility;

    @JsonProperty("Gram")
<<<<<<< HEAD
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
            List<String> values  = new ArrayList<>();

            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<String>>() {});
                    values.addAll(list);

                    break;
                case VALUE_STRING:
                    values.add(jsonParser.readValueAs(String.class));

                    break;
                default:
                    log.error("Cannot deserialize Shape");
            }
            return values;
        }
    }
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGram() { return gram; }
    @JsonProperty("Gram")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGram(String value) { this.gram = value; }

    @JsonProperty("Enveloped")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEnveloped() { return enveloped; }
    @JsonProperty("Enveloped")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setEnveloped(String value) { this.enveloped = value; }

    @JsonProperty("Shape")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getShape() { return shape; }
    @JsonProperty("Shape")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setShape(List<String> value) { this.shape = value; }

    @JsonProperty("Endospores")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEndospores() { return endospores; }
    @JsonProperty("Endospores")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setEndospores(String value) { this.endospores = value; }

    @JsonProperty("Motility")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMotility() { return motility; }
    @JsonProperty("Motility")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMotility(String value) { this.motility = value; }
>>>>>>> 取り込み、修正
}
