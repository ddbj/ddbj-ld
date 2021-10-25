package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// ProjectLinksは無視
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackageProject {
    private ProjectProject project;
    private Submission submission;

    @JsonProperty("Project")
    public ProjectProject getProject() { return project; }
    @JsonProperty("Project")
    public void setProject(ProjectProject value) { this.project = value; }
    @JsonProperty("Submission")
    public Submission getSubmission() { return submission; }
    @JsonProperty("Submission")
    public void setSubmission(Submission value) { this.submission = value; }
}
