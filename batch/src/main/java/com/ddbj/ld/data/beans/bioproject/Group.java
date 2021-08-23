package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Group {
    private String groupType;
    private String label;
    private ProjectIDRef projectIDRef;
    private ID id;
    private String description;

    @JsonProperty("groupType")
    public String getGroupType() { return groupType; }
    @JsonProperty("groupType")
    public void setGroupType(String value) { this.groupType = value; }

    @JsonProperty("label")
    public String getLabel() { return label; }
    @JsonProperty("label")
    public void setLabel(String value) { this.label = value; }

    @JsonProperty("ProjectIDRef")
    public ProjectIDRef getProjectIDRef() { return projectIDRef; }
    @JsonProperty("ProjectIDRef")
    public void setProjectIDRef(ProjectIDRef value) { this.projectIDRef = value; }

    @JsonProperty("ID")
    public ID getID() { return id; }
    @JsonProperty("ID")
    public void setID(ID value) { this.id = value; }

    @JsonProperty("Description")
    public String getDescription() { return description; }
    @JsonProperty("Description")
    public void setDescription(String value) { this.description = value; }
}
