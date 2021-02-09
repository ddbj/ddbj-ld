package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ProjectProject {
    private ProjectID projectID;
    private ProjectDescr projectDescr;
    private ProjectType projectType;
    private ProjectTypeTopAdmin projectTypeTopAdmin;
    private String descriptionSubtypeOther;

    @JsonProperty("ProjectID")
    public ProjectID getProjectID() { return projectID; }
    @JsonProperty("ProjectID")
    public void setProjectID(ProjectID value) { this.projectID = value; }

    @JsonProperty("ProjectDescr")
    public ProjectDescr getProjectDescr() { return projectDescr; }
    @JsonProperty("ProjectDescr")
    public void setProjectDescr(ProjectDescr value) { this.projectDescr = value; }

    @JsonProperty("ProjectType")
    public ProjectType getProjectType() { return projectType; }
    @JsonProperty("ProjectType")
    public void setProjectType(ProjectType value) { this.projectType = value; }

    @JsonProperty("ProjectTypeTopAdmin")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProjectTypeTopAdmin getProjectTypeTopAdmin() { return projectTypeTopAdmin; }
    @JsonProperty("ProjectTypeTopAdmin")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectTypeTopAdmin(ProjectTypeTopAdmin value) { this.projectTypeTopAdmin = value; }

    @JsonProperty("DescriptionSubtypeOther")
    public String getDescriptionSubtypeOther() { return descriptionSubtypeOther; }
    @JsonProperty("DescriptionSubtypeOther")
    public void setDescriptionSubtypeOther(String value) { this.descriptionSubtypeOther = value; }
}
