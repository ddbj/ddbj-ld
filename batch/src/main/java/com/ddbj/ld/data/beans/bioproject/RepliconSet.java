package com.ddbj.ld.data.beans.bioproject;

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
public class RepliconSet {
    private List<Replicon> replicon;
    private Ploidy ploidy;
    private List<Count> count;

    @JsonProperty("Replicon")
<<<<<<< HEAD
    @JsonDeserialize(using = RepliconSet.RepliconDeserializer.class)
    public List<Replicon> getReplicon() { return replicon; }
    @JsonProperty("Replicon")
    @JsonDeserialize(using = RepliconSet.RepliconDeserializer.class)
    public void setReplicon(List<Replicon> value) { this.replicon = value; }

    @JsonProperty("Ploidy")
    public Ploidy getPloidy() { return ploidy; }
    @JsonProperty("Ploidy")
    public void setPloidy(Ploidy value) { this.ploidy = value; }

    @JsonProperty("Count")
    @JsonDeserialize(using = RepliconSet.CountDeserializer.class)
    public List<Count> getCount() { return count; }
    @JsonProperty("Count")
    @JsonDeserialize(using = RepliconSet.CountDeserializer.class)
    public void setCount(List<Count> value) { this.count = value; }

    static class RepliconDeserializer extends JsonDeserializer<List<Replicon>> {
        @Override
        public List<Replicon> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Replicon> values = new ArrayList<>();

            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Replicon>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Replicon.class);
                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize Replicon");
            }
            return values;
        }
    }

    static class CountDeserializer extends JsonDeserializer<List<Count>> {
        @Override
        public List<Count> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Count> values = new ArrayList<>();

            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Count>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Count.class);
                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize Count");
            }
            return values;
        }
    }
}
