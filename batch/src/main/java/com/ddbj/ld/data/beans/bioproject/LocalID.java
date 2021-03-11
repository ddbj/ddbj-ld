package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class LocalID {
    private String submissionID;
    private String content;

    @JsonProperty("submission_id")
    public String getSubmissionID() { return submissionID; }
    @JsonProperty("submission_id")
    public void setSubmissionID(String value) { this.submissionID = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }
}
