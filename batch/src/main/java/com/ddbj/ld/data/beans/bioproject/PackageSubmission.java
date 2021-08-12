package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PackageSubmission {
    private SubmissionSubmission submission;
    private SubmissionProjectAssembly projectAssembly;
    private String projectSubmission;
    private ProjectLinks projectLinks;
    private String projectPresentation;

    @JsonProperty("Submission")
    public SubmissionSubmission getSubmission() { return submission; }
    @JsonProperty("Submission")
    public void setSubmission(SubmissionSubmission value) { this.submission = value; }

    @JsonProperty("ProjectAssembly")
    public SubmissionProjectAssembly getProjectAssembly() { return projectAssembly; }
    @JsonProperty("ProjectAssembly")
    public void setProjectAssembly(SubmissionProjectAssembly value) { this.projectAssembly = value; }

    @JsonProperty("ProjectSubmission")
    public String getProjectSubmission() { return projectSubmission; }
    @JsonProperty("ProjectSubmission")
    public void setProjectSubmission(String value) { this.projectSubmission = value; }

    @JsonProperty("ProjectLinks")
    @JsonIgnoreProperties
    public ProjectLinks getProjectLinks() { return projectLinks; }
    @JsonProperty("ProjectLinks")
    @JsonIgnoreProperties
    public void setProjectLinks(ProjectLinks value) { this.projectLinks = value; }

    @JsonProperty("ProjectPresentation")
    public String getProjectPresentation() { return projectPresentation; }
    @JsonProperty("ProjectPresentation")
    public void setProjectPresentation(String value) { this.projectPresentation = value; }
}
