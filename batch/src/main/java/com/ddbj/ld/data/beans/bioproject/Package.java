package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Package {
    private List<Processing> processing;
    private PackageProject project;
    private String projectAssembly;
    private String projectSubmission;
    private String projectLinks;
    private String projectPresentation;

    @JsonProperty("Processing")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Processing> getProcessing() { return processing; }
    @JsonProperty("Processing")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProcessing(List<Processing> value) { this.processing = value; }

    @JsonProperty("Project")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PackageProject getProject() { return project; }
    @JsonProperty("Project")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProject(PackageProject value) { this.project = value; }

    @JsonProperty("ProjectAssembly")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getProjectAssembly() { return projectAssembly; }
    @JsonProperty("ProjectAssembly")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectAssembly(String value) { this.projectAssembly = value; }

    @JsonProperty("ProjectSubmission")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getProjectSubmission() { return projectSubmission; }
    @JsonProperty("ProjectSubmission")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectSubmission(String value) { this.projectSubmission = value; }

    @JsonProperty("ProjectLinks")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getProjectLinks() { return projectLinks; }
    @JsonProperty("ProjectLinks")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectLinks(String value) { this.projectLinks = value; }

    @JsonProperty("ProjectPresentation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getProjectPresentation() { return projectPresentation; }
    @JsonProperty("ProjectPresentation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectPresentation(String value) { this.projectPresentation = value; }

}
