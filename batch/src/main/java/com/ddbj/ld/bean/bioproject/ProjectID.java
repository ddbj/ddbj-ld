package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ProjectID {
    private ArchiveID archiveID;
    private CenterID centerID;
    private String localID;

    @JsonProperty("ArchiveID")
    public ArchiveID getArchiveID() { return archiveID; }
    @JsonProperty("ArchiveID")
    public void setArchiveID(ArchiveID value) { this.archiveID = value; }

    @JsonProperty("CenterID")
    public CenterID getCenterID() { return centerID; }
    @JsonProperty("CenterID")
    public void setCenterID(CenterID value) { this.centerID = value; }

    @JsonProperty("LocalID")
    public String getLocalID() { return localID; }
    @JsonProperty("LocalID")
    public void setLocalID(String value) { this.localID = value; }
}
