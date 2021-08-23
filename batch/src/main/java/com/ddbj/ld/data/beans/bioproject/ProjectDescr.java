package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProjectDescr {
    private String name;
    private String title;
    private String description;
    private List<ExternalLink> externalLink;
    private List<Grant> grant;
    private List<Publication> publication;
    private OffsetDateTime projectReleaseDate;
    private List<String> keyword;
    private Relevance relevance;
    private List<LocusTagPrefix> locusTagPrefix;
    private List<UserTerm> userTerm;
    private RefSeq refSeq;

    @JsonProperty("Name")
    public String getName() { return name; }
    @JsonProperty("Name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("Title")
    public String getTitle() { return title; }
    @JsonProperty("Title")
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("Description")
    public String getDescription() { return description; }
    @JsonProperty("Description")
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("ExternalLink")
    @JsonDeserialize(using = ProjectDescr.ExternalLinkDeserializer.class)
    public List<ExternalLink> getExternalLink() { return externalLink; }
    @JsonProperty("ExternalLink")
    @JsonDeserialize(using = ProjectDescr.ExternalLinkDeserializer.class)
    public void setExternalLink(List<ExternalLink> value) { this.externalLink = value; }

    @JsonProperty("Grant")
    @JsonDeserialize(using = ProjectDescr.GrantDeserializer.class)
    public List<Grant> getGrant() { return grant; }
    @JsonProperty("Grant")
    @JsonDeserialize(using = ProjectDescr.GrantDeserializer.class)
    public void setGrant(List<Grant> value) { this.grant = value; }

    @JsonProperty("Publication")
    @JsonDeserialize(using = ProjectDescr.PublicationDeserializer.class)
    public List<Publication> getPublication() { return publication; }
    @JsonProperty("Publication")
    @JsonDeserialize(using = ProjectDescr.PublicationDeserializer.class)
    public void setPublication(List<Publication> value) { this.publication = value; }

    @JsonProperty("ProjectReleaseDate")
    // FIXME 2021-07-30といった形式も入れられるようにする
    public OffsetDateTime getProjectReleaseDate() { return projectReleaseDate; }
    @JsonProperty("ProjectReleaseDate")
    public void setProjectReleaseDate(OffsetDateTime value) { this.projectReleaseDate = value; }

    @JsonProperty("Keyword")
    @JsonDeserialize(using = ProjectDescr.KeywordDeserializer.class)
    public List<String> getKeyword() { return keyword; }
    @JsonProperty("Keyword")
    @JsonDeserialize(using = ProjectDescr.KeywordDeserializer.class)
    public void setKeyword(List<String> value) { this.keyword = value; }

    @JsonProperty("Relevance")
    public Relevance getRelevance() { return relevance; }
    @JsonProperty("Relevance")
    public void setRelevance(Relevance value) { this.relevance = value; }

    @JsonProperty("LocusTagPrefix")
    @JsonDeserialize(using = ProjectDescr.LocusTagPrefixDeserializer.class)
    public List<LocusTagPrefix> getLocusTagPrefix() { return locusTagPrefix; }
    @JsonProperty("LocusTagPrefix")
    @JsonDeserialize(using = ProjectDescr.LocusTagPrefixDeserializer.class)
    public void setLocusTagPrefix(List<LocusTagPrefix> value) { this.locusTagPrefix = value; }

    @JsonProperty("UserTerm")
    @JsonDeserialize(using = ProjectDescr.UserTermDeserializer.class)
    public List<UserTerm> getUserTerm() { return userTerm; }
    @JsonProperty("UserTerm")
    @JsonDeserialize(using = ProjectDescr.UserTermDeserializer.class)
    public void setUserTerm(List<UserTerm> value) { this.userTerm = value; }

    @JsonProperty("RefSeq")
    public RefSeq getRefSeq() { return refSeq; }
    @JsonProperty("RefSeq")
    public void setRefSeq(RefSeq value) { this.refSeq = value; }

    static class ExternalLinkDeserializer extends JsonDeserializer<List<ExternalLink>> {
        @Override
        public List<ExternalLink> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<ExternalLink> values = new ArrayList<>();

            var value  = new ExternalLink();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<ExternalLink>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value = Converter.getObjectMapper().readValue(jsonParser, ExternalLink.class);
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
            List<Grant> values = new ArrayList<>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<Grant>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = Converter.getObjectMapper().readValue(jsonParser, Grant.class);
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
            List<Publication> values = new ArrayList<>();

            var value  = new Publication();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<Publication>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value = Converter.getObjectMapper().readValue(jsonParser, Publication.class);
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
            List<LocusTagPrefix> values = new ArrayList<>();

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
                    var list = Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<LocusTagPrefix>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value = Converter.getObjectMapper().readValue(jsonParser, LocusTagPrefix.class);
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
            List<UserTerm> values = new ArrayList<>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<UserTerm>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = Converter.getObjectMapper().readValue(jsonParser, UserTerm.class);

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
            List<String> values = new ArrayList<>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<String>>() {});
                    values.addAll(list);

                    break;
                case VALUE_STRING:
                    var value = Converter.getObjectMapper().readValue(jsonParser, String.class);

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
