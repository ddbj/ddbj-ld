package com.ddbj.ld.data.beans.jga.study;

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
public class StudyDeserializers {

    static class AgencyDeserializer extends JsonDeserializer<Agency> {
        @Override
        public Agency deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Agency value = new Agency();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var abbr = null == map.get("abbr") ? null : map.get("abbr").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setAbbr(abbr);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Agency.");
            }
            return value;
        }
    }

    static class GrantListDeserializer extends JsonDeserializer<List<Grant>> {
        @Override
        public List<Grant> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Grant>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = StudyConverter.mapper.readValue(jsonParser, new TypeReference<List<Grant>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = StudyConverter.mapper.readValue(jsonParser, Grant.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize GRANT list.");
            }

            return values;
        }
    }

    static class PublicationListDeserializer extends JsonDeserializer<List<Publication>> {
        @Override
        public List<Publication> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Publication>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = StudyConverter.mapper.readValue(jsonParser, new TypeReference<List<Publication>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = StudyConverter.mapper.readValue(jsonParser, Publication.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize PUBLICATION list.");
            }

            return values;
        }
    }

    static class StudyAttributeListDeserializer extends JsonDeserializer<List<StudyAttribute>> {
        @Override
        public List<StudyAttribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<StudyAttribute>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var array = jsonParser.readValueAs(ArrayList.class);

                    for(var obj : array) {
                        var map = (LinkedHashMap<String, Object>)obj;
                        var tag = null == map.get("TAG") ? null : map.get("TAG").toString();
                        var value = null == map.get("VALUE") ? null : map.get("VALUE").toString();
                        var units = null == map.get("UNITS") ? null : map.get("UNITS").toString();

                        var studyAttribute = new StudyAttribute();
                        studyAttribute.setTag(tag);
                        studyAttribute.setValue(value);
                        studyAttribute.setUnits(units);

                        values.add(studyAttribute);
                    }

                    break;
                case START_OBJECT:
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var tag = null == map.get("TAG") ? null : map.get("TAG").toString();
                    var value = null == map.get("VALUE") ? null : map.get("VALUE").toString();
                    var units = null == map.get("UNITS") ? null : map.get("UNITS").toString();

                    var studyAttribute = new StudyAttribute();
                    studyAttribute.setTag(tag);
                    studyAttribute.setValue(value);
                    studyAttribute.setUnits(units);

                    values.add(studyAttribute);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize StudyAttribute list.");
            }
            return values;
        }
    }

    static class StudyLinkListDeserializer extends JsonDeserializer<List<StudyLink>> {
        @Override
        public List<StudyLink> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<StudyLink>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = StudyConverter.mapper.readValue(jsonParser, new TypeReference<List<StudyLink>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = StudyConverter.mapper.readValue(jsonParser, StudyLink.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize StudyLink list.");
            }

            return values;
        }
    }

    static class StudyTypeListDeserializer extends JsonDeserializer<List<StudyType>> {
        @Override
        public List<StudyType> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<StudyType> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = StudyConverter.mapper.readValue(jsonParser, new TypeReference<List<StudyType>>() {});
                    values = new ArrayList<>(list);

                    break;
                case START_OBJECT:
                    var value = StudyConverter.mapper.readValue(jsonParser, StudyType.class);
                    values = new ArrayList<>();
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize StudyType list.");
            }

            return values;
        }
    }
}
