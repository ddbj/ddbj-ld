package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class SubmissionProjectAssembly {
    private ProjectAssemblyProjectAssembly projectAssembly;

    @JsonProperty("ProjectAssembly")
    public ProjectAssemblyProjectAssembly getProjectAssembly() { return projectAssembly; }
    @JsonProperty("ProjectAssembly")
    public void setProjectAssembly(ProjectAssemblyProjectAssembly value) { this.projectAssembly = value; }
}
