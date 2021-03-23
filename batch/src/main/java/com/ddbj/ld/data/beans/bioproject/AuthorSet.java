package com.ddbj.ld.data.beans.bioproject;

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
public class AuthorSet {
    private List<Author> author;

    @JsonProperty("Author")
    @JsonDeserialize(using = AuthorSet.AuthorDeserializer.class)
    public List<Author> getAuthor() { return author; }
    @JsonProperty("Author")
    @JsonDeserialize(using = AuthorSet.AuthorDeserializer.class)
    public void setAuthor(List<Author> value) { this.author = value; }

    static class AuthorDeserializer extends JsonDeserializer<List<Author>> {
        @Override
        public List<Author> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Author> values = new ArrayList<>();
            Author value = new Author();

            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Author>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value = mapper.readValue(jsonParser, Author.class);
                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize Author");
            }
            return values;
        }
    }
}
