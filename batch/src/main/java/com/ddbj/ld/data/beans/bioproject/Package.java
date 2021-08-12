package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// ArchiveIDを無視
@JsonIgnoreProperties(ignoreUnknown = true)
public class Package {
    private Processing processing;
    private PackageProject project;
//    private PackageSubmission submission;
    private Submission submission;

    @JsonProperty("Processing")
    public Processing getProcessing() { return processing; }
    @JsonProperty("Processing")
    public void setProcessing(Processing value) { this.processing = value; }

    @JsonProperty("Project")
    public PackageProject getProject() { return project; }
    @JsonProperty("Project")
    public void setProject(PackageProject value) { this.project = value; }

//    @JsonProperty("Submission")
//    public PackageSubmission getSubmission() { return submission; }
//    @JsonProperty("Submission")
//    public void setSubmission(PackageSubmission value) { this.submission = value; }
    @JsonProperty("Submission")
    public Submission getSubmission() { return submission; }
    @JsonProperty("Submission")
    public void setSubmission(Submission value) { this.submission = value; }
}
