package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Assembly {
    private Standard standard;
    private Custom custom;

    @JsonProperty("STANDARD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Assembly.StandardDeserializer.class)
    public Standard getStandard() { return standard; }
    @JsonProperty("STANDARD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Assembly.StandardDeserializer.class)
    public void setStandard(Standard value) { this.standard = value; }

    @JsonProperty("CUSTOM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Assembly.CustomDeserializer.class)
    public Custom getCustom() { return custom; }
    @JsonProperty("CUSTOM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Assembly.CustomDeserializer.class)
    public void setCustom(Custom value) { this.custom = value; }

    static class StandardDeserializer extends JsonDeserializer<Standard> {
        @Override
        public Standard deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Standard value = new Standard();
            var mapper = new ObjectMapper();
            mapper.coercionConfigFor(Standard.class).setAcceptBlankAsEmpty(true);

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_NUMBER_INT:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = mapper.readValue(jsonParser, Standard.class);;
                    break;
                default:
                    log.error("Cannot deserialize Assembly.StandardDeserializer");
            }
            return value;
        }
    }

    static class CustomDeserializer extends JsonDeserializer<Custom> {
        @Override
        public Custom deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Custom value = new Custom();
            var mapper = new ObjectMapper();
            mapper.coercionConfigFor(Custom.class).setAcceptBlankAsEmpty(true);

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_NUMBER_INT:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = mapper.readValue(jsonParser, Custom.class);;
                    break;
                default:
                    log.error("Cannot deserialize Assembly.CustomDeserializer");
            }
            return value;
        }
    }
}
