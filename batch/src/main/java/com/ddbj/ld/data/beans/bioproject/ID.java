package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ID {
    private String archive;
    private String content;

    @JsonProperty("archive")
    public String getArchive() { return archive; }
    @JsonProperty("archive")
    public void setArchive(String value) { this.archive = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
