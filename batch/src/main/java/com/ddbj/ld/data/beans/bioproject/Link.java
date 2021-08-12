package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {
    private ProjectIDRef projectIDRef;
    private Hierarchical hierarchical;
    private PeerProject peerProject;
    private ID id;
    private String linkExplanation;

    @JsonProperty("ProjectIDRef")
    public ProjectIDRef getProjectIDRef() { return projectIDRef; }
    @JsonProperty("ProjectIDRef")
    public void setProjectIDRef(ProjectIDRef value) { this.projectIDRef = value; }

    @JsonProperty("Hierarchical")
    public Hierarchical getHierarchical() { return hierarchical; }
    @JsonProperty("Hierarchical")
    public void setHierarchical(Hierarchical value) { this.hierarchical = value; }

    @JsonProperty("PeerProject")
    public PeerProject getPeerProject() { return peerProject; }
    @JsonProperty("PeerProject")
    public void setPeerProject(PeerProject value) { this.peerProject = value; }

    @JsonProperty("ID")
    public ID getID() { return id; }
    @JsonProperty("ID")
    public void setID(ID value) { this.id = value; }

    @JsonProperty("LinkExplanation")
    public String getLinkExplanation() { return linkExplanation; }
    @JsonProperty("LinkExplanation")
    public void setLinkExplanation(String value) { this.linkExplanation = value; }
}
