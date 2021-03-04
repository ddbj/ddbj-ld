package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@JsonDeserialize(using = LocusTagPrefix.Deserializer.class)
public class LocusTagPrefix {
    private String bioSampleID;
    private String assemblyID;
    private String content;

    @JsonProperty("biosample_id")
    public String getBiosampleID() { return bioSampleID; }
    @JsonProperty("biosample_id")
    public void setBiosampleID(String value) { this.bioSampleID = value; }

    @JsonProperty("assembly_id")
    public String getAssemblyID() { return assemblyID; }
    @JsonProperty("assembly_id")
    public void setAssemblyID(String value) { this.assemblyID = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }

    static class Deserializer extends JsonDeserializer<LocusTagPrefix> {
        @Override
        public LocusTagPrefix deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var value  = new LocusTagPrefix();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var bioSampleID = null == map.get("biosample_id") ? null : map.get("biosample_id").toString();
                    var assemblyID = null == map.get("assembly_id") ? null : map.get("assembly_id").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setBiosampleID(bioSampleID);
                    value.setAssemblyID(assemblyID);
                    value.setContent(content);

                    break;
                default:
                    log.error("Cannot deserialize LocusTagPrefix");
            }
            return value;
        }
    }
}
