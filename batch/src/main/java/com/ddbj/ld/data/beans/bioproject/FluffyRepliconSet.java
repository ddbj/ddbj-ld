package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FluffyRepliconSet {
    private List<PurpleReplicon> replicon;
    private Ploidy ploidy;
    private List<Count> count;

    @JsonProperty("Replicon")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<PurpleReplicon> getReplicon() { return replicon; }
    @JsonProperty("Replicon")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReplicon(List<PurpleReplicon> value) { this.replicon = value; }

    @JsonProperty("Ploidy")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Ploidy getPloidy() { return ploidy; }
    @JsonProperty("Ploidy")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPloidy(Ploidy value) { this.ploidy = value; }

    @JsonProperty("Count")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Count> getCount() { return count; }
    @JsonProperty("Count")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCount(List<Count> value) { this.count = value; }
}
