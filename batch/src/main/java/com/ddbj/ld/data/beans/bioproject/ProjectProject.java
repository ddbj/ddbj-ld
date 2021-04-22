package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ProjectProject {
    private ProjectID projectID;
    private ProjectDescr projectDescr;
    private ProjectType projectType;

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
}
