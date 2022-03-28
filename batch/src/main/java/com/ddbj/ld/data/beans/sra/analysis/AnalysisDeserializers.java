package com.ddbj.ld.data.beans.sra.analysis;

import com.ddbj.ld.data.beans.sra.common.XrefLink;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AnalysisDeserializers {

    static class DataBlockListDeserializer extends JsonDeserializer<List<DataBlock>> {
        @Override
        public List<DataBlock> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<DataBlock> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = AnalysisConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = AnalysisConverter.mapper.readValue(jsonParser, DataBlock.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize DataBlock list.");
            }
            return values;
        }
    }

    static class StandardDeserializer extends JsonDeserializer<Standard> {
        @Override
        public Standard deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Standard value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_NUMBER_INT:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = AnalysisConverter.mapper.readValue(jsonParser, Standard.class);;
                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Standard.");
            }
            return value;
        }
    }

    static class CustomDeserializer extends JsonDeserializer<Custom> {
        @Override
        public Custom deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Custom value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_NUMBER_INT:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = AnalysisConverter.mapper.readValue(jsonParser, Custom.class);;
                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Custom.");
            }
            return value;
        }
    }

    static class FilesDeserializer extends JsonDeserializer<Files> {
        @Override
        public Files deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Files value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = AnalysisConverter.mapper.readValue(jsonParser, Files.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Files.");
            }
            return value;
        }
    }

    static class FileListDeserializer extends JsonDeserializer<List<File>> {
        @Override
        public List<File> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<File> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list = AnalysisConverter.mapper.readValue(jsonParser, new TypeReference<List<File>>() {});
                    values = new ArrayList<>(list);

                    break;
                case START_OBJECT:
                    var value = AnalysisConverter.mapper.readValue(jsonParser, File.class);
                    values = new ArrayList<>();
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize File list.");
            }
            return values;
        }
    }

    static class SequenceListDeserializer extends JsonDeserializer<List<Sequence>> {
        @Override
        public List<Sequence> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Sequence> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = AnalysisConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = AnalysisConverter.mapper.readValue(jsonParser, Sequence.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Sequence list.");
            }
            return values;
        }
    }

    static class XrefLinkListDeserializer extends JsonDeserializer<List<XrefLink>> {
        @Override
        public List<XrefLink> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<XrefLink> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    values = AnalysisConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = AnalysisConverter.mapper.readValue(jsonParser, XrefLink.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize XrefLink list.");
            }
            return values;
        }
    }

    static class StudyRefListDeserializer extends JsonDeserializer<List<StudyRef>> {
        @Override
        public List<StudyRef> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<StudyRef> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = AnalysisConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<StudyRef>();
                    var value = AnalysisConverter.mapper.readValue(jsonParser, StudyRef.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize StudyRef list.");
            }
            return values;
        }
    }
}
