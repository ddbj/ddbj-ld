package com.ddbj.ld.data.beans.sra.submission;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SubmissionDeserializers {

    static class ActionListDeserializer extends JsonDeserializer<List<Action>> {
        @Override
        public List<Action> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Action> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = SubmissionConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = SubmissionConverter.mapper.readValue(jsonParser, Action.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Action list.");
            }
            return values;
        }
    }

    static class ContactListDeserializer extends JsonDeserializer<List<Contact>> {
        @Override
        public List<Contact> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Contact> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = SubmissionConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = SubmissionConverter.mapper.readValue(jsonParser, Contact.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Contact list.");
            }
            return values;
        }
    }
}
