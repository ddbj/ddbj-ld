package com.ddbj.ld.data.beans.bioproject;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.*;
=======
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
>>>>>>> 取り込み、修正

public class Group {
    private String groupType;
    private String label;
<<<<<<< HEAD
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
=======
    private ProjectIDRefUnion projectIDRef;
    private String id;
    private String description;

    @JsonProperty("groupType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGroupType() { return groupType; }
    @JsonProperty("groupType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGroupType(String value) { this.groupType = value; }

    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLabel() { return label; }
    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(String value) { this.label = value; }

    @JsonProperty("ProjectIDRef")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProjectIDRefUnion getProjectIDRef() { return projectIDRef; }
    @JsonProperty("ProjectIDRef")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectIDRef(ProjectIDRefUnion value) { this.projectIDRef = value; }

    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getID() { return id; }
    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(String value) { this.id = value; }

    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() { return description; }
    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setDescription(String value) { this.description = value; }
}
