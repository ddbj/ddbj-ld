package com.ddbj.ld.data.beans.sra.submission;

import com.fasterxml.jackson.annotation.*;

public class Submission {
    private SUBMISSIONClass submission;

    @JsonProperty("SUBMISSION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SUBMISSIONClass getSubmission() { return submission; }
    @JsonProperty("SUBMISSION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubmission(SUBMISSIONClass value) { this.submission = value; }
}
