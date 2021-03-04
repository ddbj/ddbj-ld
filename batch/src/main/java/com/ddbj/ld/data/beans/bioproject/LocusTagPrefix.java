package com.ddbj.ld.data.beans.bioproject;

<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonInclude;
>>>>>>> 取り込み、修正
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

<<<<<<< HEAD
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
=======
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
>>>>>>> 取り込み、修正
    public void setContent(String value) { this.content = value; }

    static class Deserializer extends JsonDeserializer<LocusTagPrefix> {
        @Override
        public LocusTagPrefix deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
<<<<<<< HEAD
            var value  = new LocusTagPrefix();
=======
            LocusTagPrefix value = new LocusTagPrefix();
>>>>>>> 取り込み、修正

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
<<<<<<< HEAD
=======
                case VALUE_NUMBER_INT:
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
>>>>>>> 取り込み、修正
                case VALUE_STRING:
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

<<<<<<< HEAD
                    var bioSampleID = null == map.get("biosample_id") ? null : map.get("biosample_id").toString();
                    var assemblyID = null == map.get("assembly_id") ? null : map.get("assembly_id").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setBiosampleID(bioSampleID);
                    value.setAssemblyID(assemblyID);
=======
                    var assemblyId = null == map.get("assembly_id") ? null : map.get("assembly_id").toString();
                    var bioSampleId = null == map.get("biosample_id") ? null : map.get("biosample_id").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setAssemblyId(assemblyId);
                    value.setBioSampleId(bioSampleId);
>>>>>>> 取り込み、修正
                    value.setContent(content);

                    break;
                default:
                    log.error("Cannot deserialize LocusTagPrefix");
            }
<<<<<<< HEAD
=======

>>>>>>> 取り込み、修正
            return value;
        }
    }
}
