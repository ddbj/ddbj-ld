package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectType {
    private ProjectTypeTopSingleOrganism projectTypeTopSingleOrganism;
    private ProjectTypeTopAdmin projectTypeTopAdmin;
    private ProjectTypeSubmission projectTypeSubmission;

    @JsonProperty("ProjectTypeTopSingleOrganism")
    public ProjectTypeTopSingleOrganism getProjectTypeTopSingleOrganism() { return projectTypeTopSingleOrganism; }
    @JsonProperty("ProjectTypeTopSingleOrganism")
    public void setProjectTypeTopSingleOrganism(ProjectTypeTopSingleOrganism value) { this.projectTypeTopSingleOrganism = value; }

    @JsonProperty("ProjectTypeTopAdmin")
    public ProjectTypeTopAdmin getProjectTypeTopAdmin() { return projectTypeTopAdmin; }
    @JsonProperty("ProjectTypeTopAdmin")
    public void setProjectTypeTopAdmin(ProjectTypeTopAdmin value) { this.projectTypeTopAdmin = value; }

    @JsonProperty("ProjectTypeSubmission")
    public ProjectTypeSubmission getProjectTypeSubmission() { return projectTypeSubmission; }
    @JsonProperty("ProjectTypeSubmission")
    public void setProjectTypeSubmission(ProjectTypeSubmission value) { this.projectTypeSubmission = value; }
}
