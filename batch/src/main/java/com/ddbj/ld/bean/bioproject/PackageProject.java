package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class PackageProject {
    private ProjectProject project;

    @JsonProperty("Project")
    public ProjectProject getProject() { return project; }
    @JsonProperty("Project")
    public void setProject(ProjectProject value) { this.project = value; }
}
