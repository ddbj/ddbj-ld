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

@Slf4j
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class AuthorSet {
    @XmlElement(name="Author")
    @JsonProperty("Author")
    @JsonDeserialize(using = AuthorSet.AuthorDeserializer.class)
    private List<Author> author;

    static class AuthorDeserializer extends JsonDeserializer<List<Author>> {
        @Override
        public List<Author> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Author>();
            Author value;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<Author>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value = BioProjectConverter.getObjectMapper().readValue(jsonParser, Author.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Author");
            }
            return values;
        }
    }
}
