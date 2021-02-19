package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class PurpleRepliconSet {
    private Ploidy ploidy;
    private List<Count> count;
    private List<RepliconElement> replicon;
    private String name;
    private Size size;
    private String description;
    private List<String> synonym;

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

    @JsonProperty("Replicon")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<RepliconElement> getReplicon() { return replicon; }
    @JsonProperty("Replicon")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReplicon(List<RepliconElement> value) { this.replicon = value; }

    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() { return name; }
    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(String value) { this.name = value; }

    @JsonProperty("Size")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Size getSize() { return size; }
    @JsonProperty("Size")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSize(Size value) { this.size = value; }

    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() { return description; }
    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("Synonym")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getSynonym() { return synonym; }
    @JsonProperty("Synonym")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSynonym(List<String> value) { this.synonym = value; }
}
