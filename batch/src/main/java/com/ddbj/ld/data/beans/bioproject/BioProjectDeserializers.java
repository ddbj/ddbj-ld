package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
public class BioProjectDeserializers {

    static class AgencyDeserializer extends JsonDeserializer<Agency> {
        @Override
        public Agency deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Agency value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = new Agency();
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    value = new Agency();
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    // 無限ループとなってしまうためTypeReferenceを用いたパースは使用しない
                    value = new Agency();
                    var map = jsonParser.readValueAs(LinkedHashMap.class);

                    var abbr = null == map.get("abbr") ? null : map.get("abbr").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setAbbr(abbr);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Agency");
            }
            return value;
        }
    }

    static class RepliconListDeserializer extends JsonDeserializer<List<Replicon>> {
        @Override
        public List<Replicon> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Replicon> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioProjectConverter.mapper.readValue(jsonParser, Replicon.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Replicon list.");
            }
            return values;
        }
    }

    static class AuthorListDeserializer extends JsonDeserializer<List<Author>> {
        @Override
        public List<Author> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Author> values = null;
            Author value;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    value = BioProjectConverter.mapper.readValue(jsonParser, Author.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Author list.");
            }
            return values;
        }
    }

    static class BioSampleSetIDDeserializer extends JsonDeserializer<BioSampleSetID> {
        @Override
        public BioSampleSetID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            BioSampleSetID  value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = new BioSampleSetID();
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    value = new BioSampleSetID();
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    value = BioProjectConverter.mapper.readValue(jsonParser, BioSampleSetID.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize BioSampleSetID.");
            }
            return value;
        }
    }

    static class BioSampleSetIDListDeserializer extends JsonDeserializer<List<BioSampleSetID>> {
        @Override
        public List<BioSampleSetID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<BioSampleSetID> values = null;
            BioSampleSetID value;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    values = new ArrayList<>();
                    value = new BioSampleSetID();
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());
                    values.add(value);

                    break;
                case VALUE_STRING:
                    values = new ArrayList<>();
                    value = new BioSampleSetID();
                    value.setContent(jsonParser.readValueAs(String.class));
                    values.add(value);

                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    value = BioProjectConverter.mapper.readValue(jsonParser, BioSampleSetID.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize BioSampleSetID list.");
            }
            return values;
        }
    }

    static class OrganizationListDeserializer extends JsonDeserializer<List<Organization>> {
        @Override
        public List<Organization> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Organization> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioProjectConverter.mapper.readValue(jsonParser, Organization.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Organization list.");
            }
            return values;
        }
    }

    static class LocalIDDeserializer extends JsonDeserializer<LocalID> {
        @Override
        public LocalID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            LocalID value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = new LocalID();
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    value = new LocalID();
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    // 無限ループとなってしまうためTypeReferenceを用いたパースは使用しない
                    value = new LocalID();
                    var map = jsonParser.readValueAs(LinkedHashMap.class);

                    var submissionID = null == map.get("submission_id") ? null : map.get("submission_id").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setSubmissionID(submissionID);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize LocalID.");
            }
            return value;
        }
    }

    static class LocusTagPrefixDeserializer extends JsonDeserializer<LocusTagPrefix> {
        @Override
        public LocusTagPrefix deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            LocusTagPrefix value  = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = new LocusTagPrefix();
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    value = new LocusTagPrefix();
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    // 無限ループとなってしまうためTypeReferenceを用いたパースは使用しない
                    value = new LocusTagPrefix();
                    var map = jsonParser.readValueAs(LinkedHashMap.class);

                    var bioSampleID = null == map.get("biosample_id") ? null : map.get("biosample_id").toString();
                    var assemblyID = null == map.get("assembly_id") ? null : map.get("assembly_id").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setBioSampleID(bioSampleID);
                    value.setAssemblyID(assemblyID);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize LocusTagPrefix.");
            }
            return value;
        }
    }

    static class ObjectivesDataListDeserializer extends JsonDeserializer<List<ObjectivesData>> {
        @Override
        public List<ObjectivesData> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<ObjectivesData> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    values= BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value= BioProjectConverter.mapper.readValue(jsonParser, ObjectivesData.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ObjectivesData list.");
            }
            return values;
        }
    }

    static class OrganizationNameDeserializer extends JsonDeserializer<OrganizationName> {
        @Override
        public OrganizationName deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            OrganizationName value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = new OrganizationName();
                    value.setContent(jsonParser.readValueAs(Long.class).toString());

                    break;
                case VALUE_STRING:
                    value = new OrganizationName();
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    // 無限ループとなってしまうためTypeReferenceを用いたパースは使用しない
                    value = new OrganizationName();
                    var map = jsonParser.readValueAs(LinkedHashMap.class);

                    var abbr = null == map.get("abbr") ? null : map.get("abbr").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setAbbr(abbr);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize OrganizationName.");
            }
            return value;
        }
    }

    static class ExternalLinkListDeserializer extends JsonDeserializer<List<ExternalLink>> {
        @Override
        public List<ExternalLink> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<ExternalLink> values = null;
            ExternalLink value;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    value = BioProjectConverter.mapper.readValue(jsonParser, ExternalLink.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ExternalLink list.");
            }
            return values;
        }
    }

    static class GrantListDeserializer extends JsonDeserializer<List<Grant>> {
        @Override
        public List<Grant> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Grant> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioProjectConverter.mapper.readValue(jsonParser, Grant.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Grant list.");
            }
            return values;
        }
    }

    static class PublicationListDeserializer extends JsonDeserializer<List<Publication>> {
        @Override
        public List<Publication> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Publication> values = null;
            Publication value;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    value = BioProjectConverter.mapper.readValue(jsonParser, Publication.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Publication list.");
            }
            return values;
        }
    }

    static class LocusTagPrefixListDeserializer extends JsonDeserializer<List<LocusTagPrefix>> {
        @Override
        public List<LocusTagPrefix> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<LocusTagPrefix> values = null;
            LocusTagPrefix value;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    values = new ArrayList<>();
                    value = new LocusTagPrefix();
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());
                    values.add(value);

                    break;
                case VALUE_STRING:
                    values = new ArrayList<>();
                    value = new LocusTagPrefix();
                    value.setContent(jsonParser.readValueAs(String.class));
                    values.add(value);

                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    value = BioProjectConverter.mapper.readValue(jsonParser, LocusTagPrefix.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize LocusTagPrefix list.");
            }
            return values;
        }
    }

    static class UserTermListDeserializers extends JsonDeserializer<List<UserTerm>> {
        @Override
        public List<UserTerm> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<UserTerm> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioProjectConverter.mapper.readValue(jsonParser, UserTerm.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize UserTerm list.");
            }
            return values;
        }
    }

    static class RelevanceDeserializer extends JsonDeserializer<Relevance> {
        @Override
        public Relevance deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Relevance value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    // 無限ループとなってしまうためTypeReferenceを用いたパースは使用しない
                    value = new Relevance();
                    var map = jsonParser.readValueAs(LinkedHashMap.class);

                    var agricultural = null == map.get("Agricultural") ? null : map.get("Agricultural").toString();
                    var medical = null == map.get("Medical") ? null : map.get("Medical").toString();
                    var industrial = null == map.get("Industrial") ? null : map.get("Industrial").toString();
                    var environmental = null == map.get("Environmental") ? null : map.get("Environmental").toString();
                    var evolution = null == map.get("Evolution") ? null : map.get("Evolution").toString();
                    var modelOrganism = null == map.get("ModelOrganism") ? null : map.get("ModelOrganism").toString();
                    var other = null == map.get("Other") ? null : map.get("Other").toString();

                    value.setAgricultural(agricultural);
                    value.setMedical(medical);
                    value.setIndustrial(industrial);
                    value.setEnvironmental(environmental);
                    value.setEvolution(evolution);
                    value.setModelOrganism(modelOrganism);
                    value.setOther(other);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Relevance.");
            }
            return value;
        }
    }


    static class ArchiveIDListDeserializer extends JsonDeserializer<List<ArchiveID>> {
        @Override
        public List<ArchiveID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<ArchiveID> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value= BioProjectConverter.mapper.readValue(jsonParser, ArchiveID.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ArchiveID list");
            }
            return values;
        }
    }

    static class LocalIDListDeserializer extends JsonDeserializer<List<LocalID>> {
        @Override
        public List<LocalID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<LocalID> values = null;
            LocalID value;

            switch (jsonParser.currentToken()) {
                case VALUE_NUMBER_INT:
                    values = new ArrayList<>();
                    value = new LocalID();
                    value.setContent(jsonParser.readValueAs(Long.class).toString());

                    values.add(value);

                    break;
                case VALUE_STRING:
                    values = new ArrayList<>();
                    value = new LocalID();
                    value.setContent(jsonParser.readValueAs(String.class));

                    values.add(value);

                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    value = BioProjectConverter.mapper.readValue(jsonParser, LocalID.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize LocalID list.");
            }
            return values;
        }
    }

    static class CenterIDListDeserializer extends JsonDeserializer<List<CenterID>> {
        @Override
        public List<CenterID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<CenterID> values = null;
            CenterID value;

            switch (jsonParser.currentToken()) {
                case VALUE_NUMBER_INT:
                    values = new ArrayList<>();
                    value  = new CenterID();
                    value.setContent(jsonParser.readValueAs(Long.class).toString());

                    values.add(value);

                    break;
                case VALUE_STRING:
                    values = new ArrayList<>();
                    value  = new CenterID();
                    value.setContent(jsonParser.readValueAs(String.class));

                    values.add(value);

                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    value = BioProjectConverter.mapper.readValue(jsonParser, CenterID.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize CenterID list.");
            }
            return values;
        }
    }

    static class CountListDeserializer extends JsonDeserializer<List<Count>> {
        @Override
        public List<Count> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ArrayList<Count> values = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    values = BioProjectConverter.mapper.readValue(jsonParser, new TypeReference<>() {});

                    break;
                case START_OBJECT:
                    values = new ArrayList<>();
                    var value = BioProjectConverter.mapper.readValue(jsonParser, Count.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Count list.");
            }
            return values;
        }
    }


}
