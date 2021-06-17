package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;
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
public class Body {
    private List<Header> row;

    @JsonProperty("Row")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Body.HeaderDeserializer.class)
    public List<Header> getRow() { return row; }
    @JsonProperty("Row")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Body.HeaderDeserializer.class)
    public void setRow(List<Header> value) { this.row = value; }

    static class HeaderDeserializer extends JsonDeserializer<List<Header>> {
        @Override
        public List<Header> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Header> values = new ArrayList<>();
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Header>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Header.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Body.HeaderDeserializer");
            }
            return values;
        }
    }
}
