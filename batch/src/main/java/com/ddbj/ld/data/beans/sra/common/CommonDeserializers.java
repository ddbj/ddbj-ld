package com.ddbj.ld.data.beans.sra.common;

import com.ddbj.ld.data.beans.sra.experiment.ExperimentConverter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CommonDeserializers {

    public static class AttributeDeserializer extends JsonDeserializer<Attribute> {
        @Override
        public Attribute deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Attribute values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_NUMBER_INT:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    values = new Attribute();
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var tag = null == map.get("TAG") ? null : map.get("TAG").toString();
                    var value = null == map.get("VALUE") ? null : map.get("VALUE").toString();
                    var units = null == map.get("UNITS") ? null : map.get("UNITS").toString();

                    values.setTag(tag);
                    values.setValue(value);
                    values.setUnits(units);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Attribute.");
            }
            return values;
        }
    }

    public static class AttributeListDeserializer extends JsonDeserializer<List<Attribute>> {
        @Override
        public List<Attribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Attribute> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    values = CommonConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = CommonConverter.mapper.readValue(jsonParser, Attribute.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Attribute list.");
            }
            return values;
        }
    }

    public static class LinkListDeserializer extends JsonDeserializer<List<Link>> {
        @Override
        public List<Link> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Link> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = CommonConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = CommonConverter.mapper.readValue(jsonParser, Link.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Link list.");
            }
            return values;
        }
    }

    public static class BasecallDeserializer extends JsonDeserializer<Basecall> {
        @Override
        public Basecall deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Basecall value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = new Basecall();
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    value = new Basecall();
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    value = new Basecall();
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var readGroupTag = null == map.get("read_group_tag") ? null : map.get("read_group_tag").toString();
                    var minMatch = null == map.get("min_match") ? null : map.get("min_match").toString();
                    var maxMismatch = null == map.get("max_mismatch") ? null : map.get("max_mismatch").toString();
                    var matchEdge = null == map.get("match_edge") ? null : map.get("match_edge").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setReadGroupTag(readGroupTag);
                    value.setMinMatch(minMatch);
                    value.setMaxMismatch(maxMismatch);
                    value.setMatchEdge(matchEdge);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Basecall.");
            }
            return value;
        }
    }

    public static class BasecallListDeserializer extends JsonDeserializer<List<Basecall>> {
        @Override
        public List<Basecall> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Basecall> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = CommonConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = CommonConverter.mapper.readValue(jsonParser, Basecall.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Basecall list.");
            }
            return values;
        }
    }

    public static class IDDeserializer extends JsonDeserializer<ID> {
        @Override
        public ID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ID value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = new ID();
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    value = new ID();
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    value = new ID();
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var label = null == map.get("label") ? null : map.get("label").toString();
                    var namespace  = null == map.get("namespace") ? null : map.get("namespace").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setLabel(label);
                    value.setNamespace(namespace);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ID.");
            }
            return value;
        }
    }

    public static class IDListDeserializer extends JsonDeserializer<List<ID>> {
        @Override
        public List<ID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<ID> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = CommonConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = CommonConverter.mapper.readValue(jsonParser, ID.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ID list.");
            }
            return values;
        }
    }

    public static class PrimaryIDListDeserializer extends JsonDeserializer<List<PrimaryID>> {
        @Override
        public List<PrimaryID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<PrimaryID> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = CommonConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = CommonConverter.mapper.readValue(jsonParser, PrimaryID.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize PrimaryID list.");
            }
            return values;
        }
    }

    public static class PipeSectionListDeserializer extends JsonDeserializer<List<PipeSection>> {
        @Override
        public List<PipeSection> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<PipeSection> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    values = CommonConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = CommonConverter.mapper.readValue(jsonParser, PipeSection.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize PipeSection list.");
            }
            return values;
        }
    }

    public static class PrimaryIDDeserializer extends JsonDeserializer<PrimaryID> {
        @Override
        public PrimaryID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            PrimaryID value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = new PrimaryID();
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    value = new PrimaryID();
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    value = new PrimaryID();
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var label = null == map.get("label") ? null : map.get("label").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setLabel(label);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize PrimaryID.");
            }
            return value;
        }
    }

    public static class SequencingDirectivesDeserializer extends JsonDeserializer<SequencingDirectives> {
        @Override
        public SequencingDirectives deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            SequencingDirectives value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    // 空文字があるため、削除
                    break;
                case START_OBJECT:
                    value = CommonConverter.mapper.readValue(jsonParser, SequencingDirectives.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize SequencingDirectives.");
            }
            return value;
        }
    }

    public static class RelativeOrderDeserializer extends JsonDeserializer<RelativeOrder> {
        @Override
        public RelativeOrder deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            RelativeOrder value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = CommonConverter.mapper.readValue(jsonParser, RelativeOrder.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize RelativeOrder.");
            }
            return value;
        }
    }

    public static class ReadSpecListDeserializer extends JsonDeserializer<List<ReadSpec>> {
        @Override
        public List<ReadSpec> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<ReadSpec> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    values = CommonConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = CommonConverter.mapper.readValue(jsonParser, ReadSpec.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ReadSpec list.");
            }
            return values;
        }
    }

    public static class ProcessingDeserializer extends JsonDeserializer<Processing> {
        @Override
        public Processing deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Processing value = null;
            CommonConverter.mapper.coercionConfigFor(Processing.class).setAcceptBlankAsEmpty(true);

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = CommonConverter.mapper.readValue(jsonParser, Processing.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Processing.");
            }
            return value;
        }
    }

    public static class IdentifiersDeserializer extends JsonDeserializer<Identifiers> {
        @Override
        public Identifiers deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Identifiers value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = ExperimentConverter.mapper.readValue(jsonParser, Identifiers.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Identifiers.");
            }
            return value;
        }
    }
}
