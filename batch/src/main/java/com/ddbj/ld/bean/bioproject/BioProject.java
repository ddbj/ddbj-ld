package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class BioProject {
    private Package bioProjectPackage;
    private String projectAssembly;
    private String projectSubmission;
    private String projectLinks;
    private String projectPresentation;

    @JsonProperty("Package")
    public Package getBioProjectPackage() { return bioProjectPackage; }
    @JsonProperty("Package")
    public void setBioProjectPackage(Package value) { this.bioProjectPackage = value; }

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
