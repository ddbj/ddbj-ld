package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class PackageProject {
    private ProjectProject project;
    private ProjectTypeSubmission projectTypeSubmission;

    @JsonProperty("Project")
    public ProjectProject getProject() { return project; }
    @JsonProperty("Project")
    public void setProject(ProjectProject value) { this.project = value; }

    @JsonProperty("ProjectTypeSubmission")
    public ProjectTypeSubmission getProjectTypeSubmission() { return projectTypeSubmission; }
    @JsonProperty("ProjectTypeSubmission")
    public void setProjectTypeSubmission(ProjectTypeSubmission value) { this.projectTypeSubmission = value; }
}
