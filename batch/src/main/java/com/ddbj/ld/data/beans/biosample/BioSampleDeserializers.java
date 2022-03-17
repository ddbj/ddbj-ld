package com.ddbj.ld.data.beans.biosample;

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
public class BioSampleDeserializers {

    static class AttributeListDeserializer extends JsonDeserializer<List<Attribute>> {
        @Override
        public List<Attribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Attribute> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    values = BioSampleConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioSampleConverter.mapper.readValue(jsonParser, Attribute.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Attributes list.");
            }
            return values;
        }
    }

    static class HeaderListDeserializer extends JsonDeserializer<List<Header>> {
        @Override
        public List<Header> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Header> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    values = BioSampleConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioSampleConverter.mapper.readValue(jsonParser, Header.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Header list.");
            }
            return values;
        }
    }

    static class TableListDeserializer extends JsonDeserializer<List<Table>> {
        @Override
        public List<Table> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Table> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    values = BioSampleConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioSampleConverter.mapper.readValue(jsonParser, Table.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Table list.");
            }
            return values;
        }
    }

    static class OrganismListDeserializer extends JsonDeserializer<List<BioSampleOrganism>> {
        @Override
        public List<BioSampleOrganism> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<BioSampleOrganism> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    values = BioSampleConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioSampleConverter.mapper.readValue(jsonParser, BioSampleOrganism.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Organism list.");
            }
            return values;
        }
    }

    static class SampleIdListDeserializer extends JsonDeserializer<List<SampleId>> {
        @Override
        public List<SampleId> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<SampleId> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    values = BioSampleConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioSampleConverter.mapper.readValue(jsonParser, SampleId.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize SampleId list.");
            }
            return values;
        }
    }

    static class LinkListDeserializer extends JsonDeserializer<List<Link>> {
        @Override
        public List<Link> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Link> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    values = BioSampleConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioSampleConverter.mapper.readValue(jsonParser, Link.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Link list.");
            }
            return values;
        }
    }

    static class ModelDeserializer extends JsonDeserializer<Model> {
        @Override
        public Model deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Model value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    value = new Model();
                    value.setContent(jsonParser.readValueAs(String.class));
                    break;
                case START_OBJECT:
                    value = new Model();
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var version = null == map.get("version") ? null : map.get("version").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setVersion(version);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Model.");
            }
            return value;
        }
    }

    static class ModelListDeserializer extends JsonDeserializer<List<Model>> {
        @Override
        public List<Model> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Model> values = null;
            Model value;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    values = new ArrayList<>();
                    value = new Model();
                    value.setContent(jsonParser.readValueAs(String.class));
                    values.add(value);

                    break;
                case START_ARRAY:
                    values = BioSampleConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    value = BioSampleConverter.mapper.readValue(jsonParser, Model.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Models list.");
            }
            return values;
        }
    }

    static class NameElementDeserializer extends JsonDeserializer<NameElement> {
        @Override
        public NameElement deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            NameElement value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = new NameElement();
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    value = new NameElement();
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    value = new NameElement();
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var abbreviation = null == map.get("abbreviation") ? null : map.get("abbreviation").toString();
                    var url  = null == map.get("url") ? null : map.get("url").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setAbbreviation(abbreviation);
                    value.setUrl(url);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize NameElement.");
            }
            return value;
        }
    }

    static class NameElementListDeserializer extends JsonDeserializer<List<NameElement>> {
        @Override
        public List<NameElement> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String content;
            NameElement value;
            ArrayList<NameElement> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    values = new ArrayList<>();
                    content = BioSampleConverter.mapper.readValue(jsonParser, Float.class).toString();
                    value = new NameElement();
                    value.setContent(content);
                    values.add(value);

                    break;
                case VALUE_STRING:
                    values = new ArrayList<>();
                    content = BioSampleConverter.mapper.readValue(jsonParser, String.class);
                    value = new NameElement();
                    value.setContent(content);
                    values.add(value);

                    break;
                case START_ARRAY:
                    values = BioSampleConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    value = BioSampleConverter.mapper.readValue(jsonParser, NameElement.class);
                    values.add(value);

                    break;
                case VALUE_NULL:
                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize NameElement list.");
            }
            return values;
        }
    }

    static class ToListDeserializer extends JsonDeserializer<List<To>> {
        @Override
        public List<To> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<To> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    values = BioSampleConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioSampleConverter.mapper.readValue(jsonParser, To.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize To list.");
            }
            return values;
        }
    }

    static class AttributesDeserializer extends JsonDeserializer<Attributes> {
        @Override
        public Attributes deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Attributes value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = BioSampleConverter.mapper.readValue(jsonParser, Attributes.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Attributes.");
            }
            return value;
        }
    }

    static class ContactListDeserializer extends JsonDeserializer<List<Contact>> {
        @Override
        public List<Contact> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Contact> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    values = BioSampleConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioSampleConverter.mapper.readValue(jsonParser, Contact.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Contacts list.");
            }
            return values;
        }
    }
}
