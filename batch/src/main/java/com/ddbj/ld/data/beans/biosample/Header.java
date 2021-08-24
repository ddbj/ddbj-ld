package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;
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
public class Header {
    private List<String> cell;

    @JsonProperty("Cell")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Header.StringDeserializer.class)
    public List<String> getCell() { return cell; }
    @JsonProperty("Cell")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Header.StringDeserializer.class)
    public void setCell(List<String> value) { this.cell = value; }

    static class StringDeserializer extends JsonDeserializer<List<String>> {
        @Override
        public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<String>();
            var mapper = Converter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_FLOAT:
                    values.add(jsonParser.readValueAs(Double.class).toString());
                    break;
                case VALUE_NUMBER_INT:
                    values.add(jsonParser.readValueAs(Integer.class).toString());
                    break;
                case VALUE_STRING:
                    values.add(mapper.readValue(jsonParser, String.class));
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<String>>() {});
                    values.addAll(list);
                    break;
                case START_OBJECT:
                    values.add(mapper.readValue(jsonParser, String.class));
                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Header.StringDeserializer");
            }
            return values;
        }
    }
}
