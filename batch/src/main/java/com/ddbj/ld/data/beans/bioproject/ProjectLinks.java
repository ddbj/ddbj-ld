package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

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
}
