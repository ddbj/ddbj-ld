package com.ddbj.ld.data.beans.jga.study;

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
public class Publications {
    private List<Publication> publication;

    @JsonProperty("PUBLICATION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Publications.PublicationDeserializer.class)
    public List<Publication> getPublication() { return publication; }
    @JsonProperty("PUBLICATION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Publications.PublicationDeserializer.class)
    public void setPublication(List<Publication> value) { this.publication = value; }

    static class PublicationDeserializer extends JsonDeserializer<List<Publication>> {
        @Override
        public List<Publication> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Publication>();
            var mapper = StudyConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Publication>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Publication.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize PUBLICATION");
            }

            return values;
        }
    }
}
