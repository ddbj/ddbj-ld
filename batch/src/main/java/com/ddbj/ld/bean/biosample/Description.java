package com.ddbj.ld.bean.biosample;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class Description {
    private String sampleName;
    private List<Synonym> synonym;
    private List<Organism> organism;
    private Comment comment;

    @JsonProperty("SampleName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSampleName() { return sampleName; }
    @JsonProperty("SampleName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSampleName(String value) { this.sampleName = value; }

    @JsonProperty("Synonym")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Synonym> getSynonym() { return synonym; }
    @JsonProperty("Synonym")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSynonym(List<Synonym> value) { this.synonym = value; }

    @JsonProperty("Organism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Organism> getOrganism() { return organism; }
    @JsonProperty("Organism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOrganism(List<Organism> value) { this.organism = value; }

    @JsonProperty("Comment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Comment getComment() { return comment; }
    @JsonProperty("Comment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setComment(Comment value) { this.comment = value; }
}
