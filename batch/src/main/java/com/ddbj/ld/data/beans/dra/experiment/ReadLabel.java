package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class ReadLabel {
<<<<<<< HEAD
    private String readGroupTag;
    private String content;

    @JsonProperty("read_group_tag")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadGroupTag() { return readGroupTag; }
    @JsonProperty("read_group_tag")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadGroupTag(String value) { this.readGroupTag = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
=======
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
>>>>>>> 取り込み、修正
}
