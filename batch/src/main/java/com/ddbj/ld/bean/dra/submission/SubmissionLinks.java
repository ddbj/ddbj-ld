package com.ddbj.ld.bean.dra.submission;

import com.fasterxml.jackson.annotation.*;

public class SubmissionLinks {
    private SubmissionLink submissionLink;

    @JsonProperty("SUBMISSION_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SubmissionLink getSubmissionLink() { return submissionLink; }
    @JsonProperty("SUBMISSION_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubmissionLink(SubmissionLink value) { this.submissionLink = value; }
}
