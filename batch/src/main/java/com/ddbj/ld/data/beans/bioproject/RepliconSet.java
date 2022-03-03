package com.ddbj.ld.data.beans.bioproject;

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
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class RepliconSet {
    @XmlElement(name = "Replicon")
    @JsonProperty("Replicon")
    @JsonDeserialize(using = RepliconSet.RepliconDeserializer.class)
    private List<Replicon> replicon;

    @XmlElement(name = "Ploidy")
    @JsonProperty("Ploidy")
    private Ploidy ploidy;

    @XmlElement(name = "Count")
    @JsonProperty("Count")
    @JsonDeserialize(using = RepliconSet.CountDeserializer.class)
    private List<Count> count;

    static class RepliconDeserializer extends JsonDeserializer<List<Replicon>> {
        @Override
        public List<Replicon> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Replicon>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<Replicon>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = BioProjectConverter.getObjectMapper().readValue(jsonParser, Replicon.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Replicon");
            }
            return values;
        }
    }

    static class CountDeserializer extends JsonDeserializer<List<Count>> {
        @Override
        public List<Count> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Count>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<Count>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = BioProjectConverter.getObjectMapper().readValue(jsonParser, Count.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Count");
            }
            return values;
        }
    }
}
