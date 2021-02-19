package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class Link {
    private ProjectIDRef projectIDRef;
    private List<Hierarchical> hierarchical;
    private String id;
    private String linkExplanation;
    private List<PeerProject> peerProject;

    @JsonProperty("ProjectIDRef")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProjectIDRef getProjectIDRef() { return projectIDRef; }
    @JsonProperty("ProjectIDRef")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectIDRef(ProjectIDRef value) { this.projectIDRef = value; }

    @JsonProperty("Hierarchical")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Hierarchical> getHierarchical() { return hierarchical; }
    @JsonProperty("Hierarchical")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setHierarchical(List<Hierarchical> value) { this.hierarchical = value; }

    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getID() { return id; }
    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(String value) { this.id = value; }

    @JsonProperty("LinkExplanation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLinkExplanation() { return linkExplanation; }
    @JsonProperty("LinkExplanation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLinkExplanation(String value) { this.linkExplanation = value; }

    @JsonProperty("PeerProject")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<PeerProject> getPeerProject() { return peerProject; }
    @JsonProperty("PeerProject")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPeerProject(List<PeerProject> value) { this.peerProject = value; }
}