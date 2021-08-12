package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
// centerを無視
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectID {
    private List<ArchiveID> archiveID;
    private List<ArchiveID> secondaryArchiveID;
    private List<CenterID> centerID;
    private List<LocalID> localID;

    @JsonProperty("ArchiveID")
    @JsonDeserialize(using = ProjectID.ArchiveIDDeserializer.class)
    public List<ArchiveID> getArchiveID() { return archiveID; }
    @JsonProperty("ArchiveID")
    @JsonDeserialize(using = ProjectID.ArchiveIDDeserializer.class)
    public void setArchiveID(List<ArchiveID> value) { this.archiveID = value; }

    @JsonProperty("SecondaryArchiveID")
    @JsonDeserialize(using = ProjectID.ArchiveIDDeserializer.class)
    public List<ArchiveID> getSecondaryArchiveID() { return secondaryArchiveID; }
    @JsonProperty("SecondaryArchiveID")
    @JsonDeserialize(using = ProjectID.ArchiveIDDeserializer.class)
    public void setSecondaryArchiveID(List<ArchiveID> value) { this.secondaryArchiveID = value; }

    @JsonProperty("CenterID")
    @JsonDeserialize(using = ProjectID.ProjectIDDeserializer.class)
    public List<CenterID> getCenterID() { return centerID; }
    @JsonProperty("CenterID")
    @JsonDeserialize(using = ProjectID.ProjectIDDeserializer.class)
    public void setCenterID(List<CenterID> value) { this.centerID = value; }

    @JsonProperty("LocalID")
    @JsonDeserialize(using = ProjectID.LocalIDDeserializer.class)
    public List<LocalID> getLocalID() { return localID; }
    @JsonProperty("LocalID")
    @JsonDeserialize(using = ProjectID.LocalIDDeserializer.class)
    public void setLocalID(List<LocalID> value) { this.localID = value; }

    static class ProjectIDDeserializer extends JsonDeserializer<List<ProjectID>> {
        @Override
        public List<ProjectID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<ProjectID> values = new ArrayList<>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list= Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<ProjectID>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value= Converter.getObjectMapper().readValue(jsonParser, ProjectID.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ProjectID.ProjectIDDeserializer");
            }
            return values;
        }
    }

    static class ArchiveIDDeserializer extends JsonDeserializer<List<ArchiveID>> {
        @Override
        public List<ArchiveID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<ArchiveID> values = new ArrayList<>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list= Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<ArchiveID>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value= Converter.getObjectMapper().readValue(jsonParser, ArchiveID.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ArchiveID");
            }
            return values;
        }
    }

    static class LocalIDDeserializer extends JsonDeserializer<List<LocalID>> {
        @Override
        public List<LocalID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<LocalID> values = new ArrayList<>();
            var value = new LocalID();

            switch (jsonParser.currentToken()) {
                case VALUE_NUMBER_INT:
                    value.setContent(jsonParser.readValueAs(Long.class).toString());

                    values.add(value);

                    break;
                case VALUE_STRING:
                    value.setContent(jsonParser.readValueAs(String.class));

                    values.add(value);

                    break;
                case START_ARRAY:
                    var list= Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<LocalID>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value= Converter.getObjectMapper().readValue(jsonParser, LocalID.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize LocalID");
            }
            return values;
        }
    }
}
