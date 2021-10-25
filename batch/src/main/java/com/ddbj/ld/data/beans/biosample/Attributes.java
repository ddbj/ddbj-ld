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
public class Attributes {
    private List<Attribute> attribute;

    @JsonProperty("Attribute")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Attributes.AttributesDeserializer.class)
    public List<Attribute> getAttribute() { return attribute; }
    @JsonProperty("Attribute")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Attributes.AttributesDeserializer.class)
    public void setAttribute(List<Attribute> value) { this.attribute = value; }

    static class AttributesDeserializer extends JsonDeserializer<List<Attribute>> {
        @Override
        public List<Attribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Attribute>();
            var mapper = Converter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Attribute>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Attribute.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Attributes.AttributesDeserializer");
            }
            return values;
        }
    }
}
