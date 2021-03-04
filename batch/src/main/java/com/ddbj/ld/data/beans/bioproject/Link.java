package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;
<<<<<<< HEAD

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
=======
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
>>>>>>> 取り込み、修正
}
