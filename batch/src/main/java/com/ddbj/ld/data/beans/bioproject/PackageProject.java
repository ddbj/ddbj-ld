package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class PackageProject {
    private ProjectProject project;
    private ProjectTypeSubmission projectTypeSubmission;
    private Submission submission;
    private ProjectLinks projectLinks;

    @JsonProperty("Project")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProjectProject getProject() { return project; }
    @JsonProperty("Project")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProject(ProjectProject value) { this.project = value; }

    @JsonProperty("ProjectTypeSubmission")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProjectTypeSubmission getProjectTypeSubmission() { return projectTypeSubmission; }
    @JsonProperty("ProjectTypeSubmission")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectTypeSubmission(ProjectTypeSubmission value) { this.projectTypeSubmission = value; }

    @JsonProperty("Submission")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Submission getSubmission() { return submission; }
    @JsonProperty("Submission")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubmission(Submission value) { this.submission = value; }

    @JsonProperty("ProjectLinks")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProjectLinks getProjectLinks() { return projectLinks; }
    @JsonProperty("ProjectLinks")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectLinks(ProjectLinks value) { this.projectLinks = value; }
}
