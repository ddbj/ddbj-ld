package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class BioProject {
    private Package bioProjectPackage;
    private String projectAssembly;
    private String projectSubmission;
    private String projectLinks;
    private String projectPresentation;

    @JsonProperty("Package")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Package getBioProjectPackage() { return bioProjectPackage; }
    @JsonProperty("Package")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBioProjectPackage(Package value) { this.bioProjectPackage = value; }

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
