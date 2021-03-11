package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class RepliconSet {
    private Replicon replicon;
    private Ploidy ploidy;
    private Count count;

    @JsonProperty("Replicon")
    public Replicon getReplicon() { return replicon; }
    @JsonProperty("Replicon")
    public void setReplicon(Replicon value) { this.replicon = value; }

    @JsonProperty("Ploidy")
    public Ploidy getPloidy() { return ploidy; }
    @JsonProperty("Ploidy")
    public void setPloidy(Ploidy value) { this.ploidy = value; }

    @JsonProperty("Count")
    public Count getCount() { return count; }
    @JsonProperty("Count")
    public void setCount(Count value) { this.count = value; }
}
