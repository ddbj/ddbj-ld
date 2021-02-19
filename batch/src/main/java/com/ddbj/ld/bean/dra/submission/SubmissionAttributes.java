package com.ddbj.ld.bean.dra.submission;

import com.fasterxml.jackson.annotation.*;

public class SubmissionAttributes {
    private SubmissionAttribute submissionAttribute;

    @JsonProperty("SUBMISSION_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SubmissionAttribute getSubmissionAttribute() { return submissionAttribute; }
    @JsonProperty("SUBMISSION_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubmissionAttribute(SubmissionAttribute value) { this.submissionAttribute = value; }
}
