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
public class Comment {
    private List<String> paragraph;
    private Table table;

    @JsonProperty("Paragraph")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Comment.StringDeserializer.class)
    public List<String> getParagraph() { return paragraph; }
    @JsonProperty("Paragraph")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Comment.StringDeserializer.class)
    public void setParagraph(List<String> value) { this.paragraph = value; }

    @JsonProperty("Table")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Table getTable() { return table; }
    @JsonProperty("Table")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTable(Table value) { this.table = value; }

    static class StringDeserializer extends JsonDeserializer<List<String>> {
        @Override
        public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<String> values = new ArrayList<>();
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
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
                    log.error("Cannot deserialize Comment.StringDeserializer");
            }
            return values;
        }
    }
}
