package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ChangeStatus {
    private Target target;
    private String release;
    private Hold setReleaseDate;
    private String suppress;
    private String addComment;

    @JsonProperty("Target")
    public Target getTarget() { return target; }
    @JsonProperty("Target")
    public void setTarget(Target value) { this.target = value; }

    @JsonProperty("Release")
    public String getRelease() { return release; }
    @JsonProperty("Release")
    public void setRelease(String value) { this.release = value; }

    @JsonProperty("SetReleaseDate")
    public Hold getSetReleaseDate() { return setReleaseDate; }
    @JsonProperty("SetReleaseDate")
    public void setSetReleaseDate(Hold value) { this.setReleaseDate = value; }

    @JsonProperty("Suppress")
    public String getSuppress() { return suppress; }
    @JsonProperty("Suppress")
    public void setSuppress(String value) { this.suppress = value; }

    @JsonProperty("AddComment")
    public String getAddComment() { return addComment; }
    @JsonProperty("AddComment")
    public void setAddComment(String value) { this.addComment = value; }
}
