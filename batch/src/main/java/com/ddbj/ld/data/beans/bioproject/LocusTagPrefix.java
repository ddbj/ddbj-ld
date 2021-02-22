package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = LocusTagPrefix.Deserializer.class)
@Slf4j
public class LocusTagPrefix {
    // FIXME 要整理、DDBJにもNCBIにもない属性の情報
    private String assemblyId;
    // FIXME 要整理、DDBJにもNCBIにもない属性の情報
    private String bioSampleId;
    private String content;

    @JsonProperty("assembly_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAssemblyId() { return assemblyId; }
    @JsonProperty("assembly_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAssemblyId(String value) { this.assemblyId = value; }

    @JsonProperty("biosample_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBioSampleId() { return bioSampleId; }
    @JsonProperty("biosample_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBioSampleId(String value) { this.bioSampleId = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }

    static class Deserializer extends JsonDeserializer<LocusTagPrefix> {
        @Override
        public LocusTagPrefix deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            LocusTagPrefix value = new LocusTagPrefix();

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

                    var assemblyId = null == map.get("assembly_id") ? null : map.get("assembly_id").toString();
                    var bioSampleId = null == map.get("biosample_id") ? null : map.get("biosample_id").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setAssemblyId(assemblyId);
                    value.setBioSampleId(bioSampleId);
                    value.setContent(content);

                    break;
                default:
                    log.error("Cannot deserialize LocusTagPrefix");
            }

            return value;
        }
    }
}
