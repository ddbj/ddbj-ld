package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class SubmissionAttributes {
    private SubmissionAttribute submissionAttribute;

    @JsonProperty("SUBMISSION_ATTRIBUTE")
    public SubmissionAttribute getSubmissionAttribute() { return submissionAttribute; }
    @JsonProperty("SUBMISSION_ATTRIBUTE")
    public void setSubmissionAttribute(SubmissionAttribute value) { this.submissionAttribute = value; }
}
