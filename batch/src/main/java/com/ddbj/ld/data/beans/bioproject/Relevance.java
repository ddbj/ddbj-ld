package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = Relevance.Deserializer.class)
@Slf4j
public class Relevance {
    private String agricultural;
    private String medical;
    private String industrial;
    private String environmental;
    private String evolution;
    private String modelOrganism;
    private String other;

    @JsonProperty("Agricultural")
    public String getAgricultural() { return agricultural; }
    @JsonProperty("Agricultural")
    public void setAgricultural(String value) { this.agricultural = value; }

    @JsonProperty("Medical")
    public String getMedical() { return medical; }
    @JsonProperty("Medical")
    public void setMedical(String value) { this.medical = value; }

    @JsonProperty("Industrial")
    public String getIndustrial() { return industrial; }
    @JsonProperty("Industrial")
    public void setIndustrial(String value) { this.industrial = value; }

    @JsonProperty("Environmental")
    public String getEnvironmental() { return environmental; }
    @JsonProperty("Environmental")
    public void setEnvironmental(String value) { this.environmental = value; }

    @JsonProperty("Evolution")
    public String getEvolution() { return evolution; }
    @JsonProperty("Evolution")
    public void setEvolution(String value) { this.evolution = value; }

    @JsonProperty("ModelOrganism")
    public String getModelOrganism() { return modelOrganism; }
    @JsonProperty("ModelOrganism")
    public void setModelOrganism(String value) { this.modelOrganism = value; }

    @JsonProperty("Other")
    public String getOther() { return other; }
    @JsonProperty("Other")
    public void setOther(String value) { this.other = value; }

    static class Deserializer extends JsonDeserializer<Relevance> {
        @Override
        public Relevance deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();
            var value = new Relevance();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    var str = mapper.readValue(jsonParser, String.class);

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
                    log.error("Cannot deserialize Relevance");
            }
            return value;
        }
    }
}
