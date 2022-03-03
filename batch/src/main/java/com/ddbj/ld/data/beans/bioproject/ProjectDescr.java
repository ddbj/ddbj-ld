package com.ddbj.ld.data.beans.bioproject;

import com.ddbj.ld.data.beans.common.OffsetDateTimeAdapter;
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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ProjectDescr {
    @XmlElement(name="Name")
    @JsonProperty("Name")
    private String name;

    @XmlElement(name="Title")
    @JsonProperty("Title")
    private String title;

    @XmlElement(name="Description")
    @JsonProperty("Description")
    private String description;

    @XmlElement(name="ExternalLink")
    @JsonProperty("ExternalLink")
    @JsonDeserialize(using = ProjectDescr.ExternalLinkDeserializer.class)
    private List<ExternalLink> externalLink;

    @XmlElement(name="Grant")
    @JsonProperty("Grant")
    @JsonDeserialize(using = ProjectDescr.GrantDeserializer.class)
    private List<Grant> grant;

    @XmlElement(name="Publication")
    @JsonProperty("Publication")
    @JsonDeserialize(using = ProjectDescr.PublicationDeserializer.class)
    private List<Publication> publication;

    @XmlElement(name="ProjectReleaseDate")
    @XmlJavaTypeAdapter(OffsetDateTimeAdapter.class)
    @JsonProperty("ProjectReleaseDate")
    private OffsetDateTime projectReleaseDate;

    @XmlElement(name="Keyword")
    @JsonProperty("Keyword")
    @JsonDeserialize(using = ProjectDescr.KeywordDeserializer.class)
    private List<String> keyword;

    @XmlElement(name="Relevance")
    @JsonProperty("Relevance")
    private Relevance relevance;

    @XmlElement(name="LocusTagPrefix")
    @JsonProperty("LocusTagPrefix")
    @JsonDeserialize(using = ProjectDescr.LocusTagPrefixDeserializer.class)
    private List<LocusTagPrefix> locusTagPrefix;

    @XmlElement(name="UserTerm")
    @JsonProperty("UserTerm")
    @JsonDeserialize(using = ProjectDescr.UserTermDeserializer.class)
    private List<UserTerm> userTerm;

    @XmlElement(name="RefSeq")
    @JsonProperty("RefSeq")
    private RefSeq refSeq;

    static class ExternalLinkDeserializer extends JsonDeserializer<List<ExternalLink>> {
        @Override
        public List<ExternalLink> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<ExternalLink>();

            var value  = new ExternalLink();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<ExternalLink>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value = BioProjectConverter.getObjectMapper().readValue(jsonParser, ExternalLink.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ExternalLink");
            }
            return values;
        }
    }

    static class GrantDeserializer extends JsonDeserializer<List<Grant>> {
        @Override
        public List<Grant> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Grant>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<Grant>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = BioProjectConverter.getObjectMapper().readValue(jsonParser, Grant.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Grant");
            }
            return values;
        }
    }

    static class PublicationDeserializer extends JsonDeserializer<List<Publication>> {
        @Override
        public List<Publication> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Publication>();

            var value  = new Publication();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<Publication>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value = BioProjectConverter.getObjectMapper().readValue(jsonParser, Publication.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Publication");
            }
            return values;
        }
    }

    static class LocusTagPrefixDeserializer extends JsonDeserializer<List<LocusTagPrefix>> {
        @Override
        public List<LocusTagPrefix> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<LocusTagPrefix>();
            var value  = new LocusTagPrefix();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());
                    values.add(value);

                    break;
                case VALUE_STRING:
                    value.setContent(jsonParser.readValueAs(String.class));
                    values.add(value);

                    break;
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<LocusTagPrefix>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value = BioProjectConverter.getObjectMapper().readValue(jsonParser, LocusTagPrefix.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize LocusTagPrefix");
            }
            return values;
        }
    }

    static class UserTermDeserializer extends JsonDeserializer<List<UserTerm>> {
        @Override
        public List<UserTerm> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<UserTerm>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<UserTerm>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = BioProjectConverter.getObjectMapper().readValue(jsonParser, UserTerm.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize UserTerm");
            }
            return values;
        }
    }

    static class KeywordDeserializer extends JsonDeserializer<List<String>> {
        @Override
        public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<String>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<String>>() {});
                    values.addAll(list);

                    break;
                case VALUE_STRING:
                    var value = BioProjectConverter.getObjectMapper().readValue(jsonParser, String.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Keyword");
            }
            return values;
        }
    }
}
