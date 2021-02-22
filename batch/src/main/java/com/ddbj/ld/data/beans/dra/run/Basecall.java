package com.ddbj.ld.data.beans.dra.run;

import com.fasterxml.jackson.annotation.*;

public class Basecall {
    private String readGroupTag;
    private String minMatch;
    private String maxMismatch;
    private String matchEdge;
    private String content;

    @JsonProperty("read_group_tag")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadGroupTag() { return readGroupTag; }
    @JsonProperty("read_group_tag")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadGroupTag(String value) { this.readGroupTag = value; }

    @JsonProperty("min_match")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMinMatch() { return minMatch; }
    @JsonProperty("min_match")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMinMatch(String value) { this.minMatch = value; }

    @JsonProperty("max_mismatch")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMaxMismatch() { return maxMismatch; }
    @JsonProperty("max_mismatch")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMaxMismatch(String value) { this.maxMismatch = value; }

    @JsonProperty("match_edge")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMatchEdge() { return matchEdge; }
    @JsonProperty("match_edge")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMatchEdge(String value) { this.matchEdge = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
}
