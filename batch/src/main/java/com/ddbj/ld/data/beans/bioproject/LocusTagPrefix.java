package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class LocusTagPrefix {
    private String biosampleID;
    private String assemblyID;
    private String content;

    @JsonProperty("biosample_id")
    public String getBiosampleID() { return biosampleID; }
    @JsonProperty("biosample_id")
    public void setBiosampleID(String value) { this.biosampleID = value; }

    @JsonProperty("assembly_id")
    public String getAssemblyID() { return assemblyID; }
    @JsonProperty("assembly_id")
    public void setAssemblyID(String value) { this.assemblyID = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
