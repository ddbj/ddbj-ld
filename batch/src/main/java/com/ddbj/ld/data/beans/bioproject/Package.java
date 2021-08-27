package com.ddbj.ld.data.beans.bioproject;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// ArchiveID, Submissionを無視
@JsonIgnoreProperties(ignoreUnknown = true)
public class Package implements IPropertiesBean {
    private Processing processing;
    private PackageProject project;
    private Submission submission;

    @JsonProperty("Processing")
    public Processing getProcessing() { return processing; }
    @JsonProperty("Processing")
    public void setProcessing(Processing value) { this.processing = value; }

    @JsonProperty("Project")
    public PackageProject getProject() { return project; }
    @JsonProperty("Project")
    public void setProject(PackageProject value) { this.project = value; }
}
