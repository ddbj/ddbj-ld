package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmissionLinks {
    private SubmissionLink submissionLink;

    @JsonProperty("SUBMISSION_LINK")
    public SubmissionLink getSubmissionLink() { return submissionLink; }
    @JsonProperty("SUBMISSION_LINK")
    public void setSubmissionLink(SubmissionLink value) { this.submissionLink = value; }
}
