package com.ddbj.ld.data.beans.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Deserializers {

    public static class StringListDeserializer extends JsonDeserializer<List<String>> {
        @Override
        public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<String> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    values = new ArrayList<>();
                    values.add(jsonParser.readValueAs(Long.class).toString());
                    break;
                case VALUE_NUMBER_FLOAT:
                    values = new ArrayList<>();
                    values.add(jsonParser.readValueAs(Double.class).toString());
                    break;
                case START_ARRAY:
                    values = Converter.mapper.readValue(jsonParser, new TypeReference<>() {});
                    break;
                case VALUE_STRING:
                case START_OBJECT:
                    values = new ArrayList<>();
                    values.add(Converter.mapper.readValue(jsonParser, String.class));
                    break;
                case VALUE_TRUE:
                case VALUE_FALSE:
                    values = new ArrayList<>();
                    values.add(jsonParser.readValueAs(Boolean.class).toString());
                    break;
                default:
                    log.error("Cannot deserialize StringListDeserializer(JsonTokenId:{}).", jsonParser.currentToken().id());
            }

            return values;
        }
    }

    public static class StringDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = jsonParser.readValueAs(Long.class).toString();
                    break;
                case VALUE_NUMBER_FLOAT:
                    // 1e26といった大きな数値が入ってくるためdoubleとした
                    value = jsonParser.readValueAs(Double.class).toString();
                    break;
                case VALUE_TRUE:
                case VALUE_FALSE:
                    value = jsonParser.readValueAs(Boolean.class).toString();
                    break;
                case VALUE_STRING:
                    value = jsonParser.readValueAs(String.class);
                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize StringDeserializer(JsonTokenId:{}).", jsonParser.currentToken().id());
            }
            return value;
        }
    }
}
