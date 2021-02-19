package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ChangeStatus {
    private Target target;
    private String release;
    private Hold setReleaseDate;
    private String suppress;
    private String addComment;

    @JsonProperty("Target")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Target getTarget() { return target; }
    @JsonProperty("Target")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTarget(Target value) { this.target = value; }

    @JsonProperty("Release")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRelease() { return release; }
    @JsonProperty("Release")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRelease(String value) { this.release = value; }

    @JsonProperty("SetReleaseDate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Hold getSetReleaseDate() { return setReleaseDate; }
    @JsonProperty("SetReleaseDate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSetReleaseDate(Hold value) { this.setReleaseDate = value; }

    @JsonProperty("Suppress")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSuppress() { return suppress; }
    @JsonProperty("Suppress")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSuppress(String value) { this.suppress = value; }

    @JsonProperty("AddComment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAddComment() { return addComment; }
    @JsonProperty("AddComment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAddComment(String value) { this.addComment = value; }
}
