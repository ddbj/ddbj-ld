package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class ReadLabel {
    private Title readGroupTag;
    private Title content;

    @JsonProperty("read_group_tag")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getReadGroupTag() { return readGroupTag; }
    @JsonProperty("read_group_tag")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadGroupTag(Title value) { this.readGroupTag = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(Title value) { this.content = value; }
}
