package com.ddbj.ld.data.beans.sra.experiment;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExperimentDeserializers {

    static class PairedDeserializer extends JsonDeserializer<Paired> {
        @Override
        public Paired deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Paired value = null;
            ExperimentConverter.mapper.coercionConfigFor(Paired.class).setAcceptBlankAsEmpty(true);

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = ExperimentConverter.mapper.readValue(jsonParser, Paired.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Paired.");
            }
            return value;
        }
    }

    static class ReadLabelListDeserializer extends JsonDeserializer<List<ReadLabel>> {
        @Override
        public List<ReadLabel> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<ReadLabel> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = ExperimentConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = ExperimentConverter.mapper.readValue(jsonParser, ReadLabel.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ReadLabel list.");
            }
            return values;
        }
    }

    static class MemberListDeserializer extends JsonDeserializer<List<Member>> {
        @Override
        public List<Member> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Member> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = ExperimentConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = ExperimentConverter.mapper.readValue(jsonParser, Member.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Member list.");
            }
            return values;
        }
    }

    static class LocusListDeserializer extends JsonDeserializer<List<Locus>> {
        @Override
        public List<Locus> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Locus> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = ExperimentConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = ExperimentConverter.mapper.readValue(jsonParser, Locus.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Locus list.");
            }
            return values;
        }
    }
}
