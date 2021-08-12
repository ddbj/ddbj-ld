package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmissionProjectAssembly {
    private ProjectAssembly projectAssembly;

    @JsonProperty("ProjectAssembly")
    public ProjectAssembly getProjectAssembly() { return projectAssembly; }
    @JsonProperty("ProjectAssembly")
    public void setProjectAssembly(ProjectAssembly value) { this.projectAssembly = value; }
}
