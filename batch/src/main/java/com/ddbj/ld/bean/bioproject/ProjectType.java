package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ProjectType {
    private ProjectTypeSubmission projectTypeSubmission;
    private ProjectTypeTopSingleOrganism projectTypeTopSingleOrganism;
    private ProjectTypeTopAdmin projectTypeTopAdmin;

    @JsonProperty("ProjectTypeSubmission")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProjectTypeSubmission getProjectTypeSubmission() { return projectTypeSubmission; }
    @JsonProperty("ProjectTypeSubmission")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectTypeTopSingleOrganism(ProjectTypeSubmission value) { this.projectTypeSubmission = value; }

    @JsonProperty("ProjectTypeTopSingleOrganism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProjectTypeTopSingleOrganism getProjectTypeTopSingleOrganism() { return projectTypeTopSingleOrganism; }
    @JsonProperty("ProjectTypeTopSingleOrganism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectTypeTopSingleOrganism(ProjectTypeTopSingleOrganism value) { this.projectTypeTopSingleOrganism = value; }

    @JsonProperty("ProjectTypeTopAdmin")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProjectTypeTopAdmin getProjectTypeTopAdmin() { return projectTypeTopAdmin; }
    @JsonProperty("ProjectTypeTopAdmin")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectTypeTopAdmin(ProjectTypeTopAdmin value) { this.projectTypeTopAdmin = value; }
}
