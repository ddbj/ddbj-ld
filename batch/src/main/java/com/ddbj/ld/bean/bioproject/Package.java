package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Package {
    private Processing processing;
    private PackageProject project;
    private String projectAssembly;
    private String projectSubmission;
    private String projectLinks;
    private String projectPresentation;

    @JsonProperty("Processing")
    public Processing getProcessing() { return processing; }
    @JsonProperty("Processing")
    public void setProcessing(Processing value) { this.processing = value; }

    @JsonProperty("Project")
    public PackageProject getProject() { return project; }
    @JsonProperty("Project")
    public void setProject(PackageProject value) { this.project = value; }

    @JsonProperty("ProjectAssembly")
    public String getProjectAssembly() { return projectAssembly; }
    @JsonProperty("ProjectAssembly")
    public void setProjectAssembly(String value) { this.projectAssembly = value; }

    @JsonProperty("ProjectSubmission")
    public String getProjectSubmission() { return projectSubmission; }
    @JsonProperty("ProjectSubmission")
    public void setProjectSubmission(String value) { this.projectSubmission = value; }

    @JsonProperty("ProjectLinks")
    public String getProjectLinks() { return projectLinks; }
    @JsonProperty("ProjectLinks")
    public void setProjectLinks(String value) { this.projectLinks = value; }

    @JsonProperty("ProjectPresentation")
    public String getProjectPresentation() { return projectPresentation; }
    @JsonProperty("ProjectPresentation")
    public void setProjectPresentation(String value) { this.projectPresentation = value; }
}
