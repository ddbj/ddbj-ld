package com.ddbj.ld.data.beans.dra.common;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = Attribute.AttributeDeserializer.class)
@Slf4j
public class Attribute {
    private String tag;
    private String value;
    private String units;

    @JsonProperty("TAG")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTag() { return tag; }
    @JsonProperty("TAG")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTag(String value) { this.tag = value; }

    @JsonProperty("VALUE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getValue() { return value; }
    @JsonProperty("VALUE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setValue(String value) { this.value = value; }

    @JsonProperty("UNITS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUnits() { return units; }
    @JsonProperty("UNITS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUnits(String value) { this.units = value; }


    static class AttributeDeserializer extends JsonDeserializer<Attribute> {
        @Override
        public Attribute deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Attribute values = new Attribute();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_NUMBER_INT:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var tag = null == map.get("TAG") ? null : map.get("TAG").toString();
                    var value = null == map.get("VALUE") ? null : map.get("VALUE").toString();
                    var units = null == map.get("UNITS") ? null : map.get("UNITS").toString();

                    values.setTag(tag);
                    values.setValue(value);
                    values.setUnits(units);

                    break;
                default:
                    log.error("Cannot deserialize Attribute.AttributeDeserializer");
            }
            return values;
        }
    }
}
