package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;
<<<<<<< HEAD

public class ProjectLinks {
    private Link link;
    private Group group;

    @JsonProperty("Link")
    public Link getLink() { return link; }
    @JsonProperty("Link")
    public void setLink(Link value) { this.link = value; }

    @JsonProperty("Group")
    public Group getGroup() { return group; }
    @JsonProperty("Group")
    public void setGroup(Group value) { this.group = value; }
=======
import java.util.List;

public class ProjectLinks {
    private List<Link> link;
    private List<Group> group;
    private List<PeerProject> peerProject;

    @JsonProperty("Link")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Link> getLink() { return link; }
    @JsonProperty("Link")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLink(List<Link> value) { this.link = value; }

    @JsonProperty("Group")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Group> getGroup() { return group; }
    @JsonProperty("Group")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGroup(List<Group> value) { this.group = value; }

    @JsonProperty("PeerProject")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<PeerProject> getPeerProject() { return peerProject; }
    @JsonProperty("PeerProject")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPeerProject(List<PeerProject> value) { this.peerProject = value; }
>>>>>>> 取り込み、修正
}
