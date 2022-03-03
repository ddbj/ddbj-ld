package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
// centerを無視
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ProjectID {
    @XmlElement(name="ArchiveID")
    @JsonProperty("ArchiveID")
    @JsonDeserialize(using = ProjectID.ArchiveIDDeserializer.class)
    private List<ArchiveID> archiveID;

    @XmlElement(name="SecondaryArchiveID")
    @JsonProperty("SecondaryArchiveID")
    @JsonDeserialize(using = ProjectID.ArchiveIDDeserializer.class)
    private List<ArchiveID> secondaryArchiveID;

    @XmlElement(name="CenterID")
    @JsonProperty("CenterID")
    @JsonDeserialize(using = ProjectID.CenterIDDeserializer.class)
    private List<CenterID> centerID;

    @XmlElement(name="LocalID")
    @JsonProperty("LocalID")
    @JsonDeserialize(using = ProjectID.LocalIDDeserializer.class)
    private List<LocalID> localID;

    static class ArchiveIDDeserializer extends JsonDeserializer<List<ArchiveID>> {
        @Override
        public List<ArchiveID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<ArchiveID>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list= BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<ArchiveID>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value= BioProjectConverter.getObjectMapper().readValue(jsonParser, ArchiveID.class);

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
            var values = new ArrayList<LocalID>();
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
                    var list= BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<LocalID>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value= BioProjectConverter.getObjectMapper().readValue(jsonParser, LocalID.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize LocalID");
            }
            return values;
        }
    }

    static class CenterIDDeserializer extends JsonDeserializer<List<CenterID>> {
        @Override
        public List<CenterID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<CenterID>();
            var value = new CenterID();

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
                    var list= BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<CenterID>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value= BioProjectConverter.getObjectMapper().readValue(jsonParser, CenterID.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize CenterID");
            }
            return values;
        }
    }
}
