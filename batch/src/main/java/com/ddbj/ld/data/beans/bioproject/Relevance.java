package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = Relevance.Deserializer.class)
@Slf4j
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Relevance {
    @XmlElement(name="Agricultural")
    @JsonProperty("Agricultural")
    private String agricultural;

    @XmlElement(name="Medical")
    @JsonProperty("Medical")
    private String medical;

    @XmlElement(name="Industrial")
    @JsonProperty("Industrial")
    private String industrial;

    @XmlElement(name="Environmental")
    @JsonProperty("Environmental")
    private String environmental;

    @XmlElement(name="Evolution")
    @JsonProperty("Evolution")
    private String evolution;

    @XmlElement(name="ModelOrganism")
    @JsonProperty("ModelOrganism")
    private String modelOrganism;

    @XmlElement(name="Other")
    @JsonProperty("Other")
    private String other;

    static class Deserializer extends JsonDeserializer<Relevance> {
        @Override
        public Relevance deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var value = new Relevance();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    var str= BioProjectConverter.getObjectMapper().readValue(jsonParser, String.class);

                    if(str.isBlank()) {
                        value = null;
                    } else {
                        // 空以外の文字列なら、例外を発する
                        // FIXME 暫定対応
                        throw new IOException();
                    }

                    break;
                case START_OBJECT:
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

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
                    log.error("Cannot deserialize Relevance");
            }
            return value;
        }
    }
}
