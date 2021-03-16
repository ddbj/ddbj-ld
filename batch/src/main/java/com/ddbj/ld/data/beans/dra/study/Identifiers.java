package com.ddbj.ld.data.beans.dra.study;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Identifiers {
    private PrimaryID primaryID;
    private List<PrimaryID> secondaryID;
    private List<ID> externalID;
    private List<ID> submitterID;
    private List<PrimaryID> uuid;

    @JsonProperty("PRIMARY_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PrimaryID getPrimaryID() { return primaryID; }
    @JsonProperty("PRIMARY_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPrimaryID(PrimaryID value) { this.primaryID = value; }

    @JsonProperty("SECONDARY_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Identifiers.PrimaryIDDeserializer.class)
    public List<PrimaryID> getSecondaryID() { return secondaryID; }
    @JsonProperty("SECONDARY_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Identifiers.PrimaryIDDeserializer.class)
    public void setSecondaryID(List<PrimaryID> value) { this.secondaryID = value; }

    @JsonProperty("EXTERNAL_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Identifiers.IDDeserializer.class)
    public List<ID> getExternalID() { return externalID; }
    @JsonProperty("EXTERNAL_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Identifiers.IDDeserializer.class)
    public void setExternalID(List<ID> value) { this.externalID = value; }

    @JsonProperty("SUBMITTER_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Identifiers.IDDeserializer.class)
    public List<ID> getSubmitterID() { return submitterID; }
    @JsonProperty("SUBMITTER_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Identifiers.IDDeserializer.class)
    public void setSubmitterID(List<ID> value) { this.submitterID = value; }

    @JsonProperty("UUID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Identifiers.PrimaryIDDeserializer.class)
    public List<PrimaryID> getUUID() { return uuid; }
    @JsonProperty("UUID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Identifiers.PrimaryIDDeserializer.class)
    public void setUUID(List<PrimaryID> value) { this.uuid = value; }

    static class IDDeserializer extends JsonDeserializer<List<ID>> {
        @Override
        public List<ID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<ID> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<ID>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, ID.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize IDDeserializer");
            }
            return values;
        }
    }

    static class PrimaryIDDeserializer extends JsonDeserializer<List<PrimaryID>> {
        @Override
        public List<PrimaryID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<PrimaryID> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<PrimaryID>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, PrimaryID.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize PrimaryIDDeserializer");
            }
            return values;
        }
    }
}
