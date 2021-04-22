package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ProjectID {
    private ArchiveID archiveID;
    private ArchiveID secondaryArchiveID;
    private CenterID centerID;
    private LocalID localID;

    @JsonProperty("ArchiveID")
    public ArchiveID getArchiveID() { return archiveID; }
    @JsonProperty("ArchiveID")
    public void setArchiveID(ArchiveID value) { this.archiveID = value; }

    @JsonProperty("SecondaryArchiveID")
    public ArchiveID getSecondaryArchiveID() { return secondaryArchiveID; }
    @JsonProperty("SecondaryArchiveID")
    public void setSecondaryArchiveID(ArchiveID value) { this.secondaryArchiveID = value; }

    @JsonProperty("CenterID")
    public CenterID getCenterID() { return centerID; }
    @JsonProperty("CenterID")
    public void setCenterID(CenterID value) { this.centerID = value; }

    @JsonProperty("LocalID")
    public LocalID getLocalID() { return localID; }
    @JsonProperty("LocalID")
    public void setLocalID(LocalID value) { this.localID = value; }
}
