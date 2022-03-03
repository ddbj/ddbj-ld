package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@Slf4j
public class Morphology {
    @XmlElement(name="Gram")
    @JsonProperty("Gram")
    private String gram;

    @XmlElement(name="Enveloped")
    @JsonProperty("Enveloped")
    private String enveloped;

    @XmlElement(name="Shape")
    @JsonProperty("Shape")
    @JsonDeserialize(using = Morphology.ShapeDeserializer.class)
    private List<String> shape;

    @XmlElement(name="Endospores")
    @JsonProperty("Endospores")
    private String endospores;

    @XmlElement(name="Motility")
    @JsonProperty("Motility")
    private String motility;

    static class ShapeDeserializer extends JsonDeserializer<List<String>> {
        @Override
        public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values  = new ArrayList<String>();

            switch (jsonParser.currentToken()) {
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<String>>() {});
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
