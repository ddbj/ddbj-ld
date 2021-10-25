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
public class Relation {
    private List<To> to;
    private String type;

    @JsonProperty("To")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Relation.ToDeserializer.class)
    public List<To> getTo() { return to; }
    @JsonProperty("To")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Relation.ToDeserializer.class)
    public void setTo(List<To> value) { this.to = value; }

    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getType() { return type; }
    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setType(String value) { this.type = value; }

    static class ToDeserializer extends JsonDeserializer<List<To>> {
        @Override
        public List<To> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<To>();
            var mapper = Converter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<To>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, To.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Relation.ToDeserializer");
            }
            return values;
        }
    }
}
