package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}
