package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class ProjectID {
    private List<ArchiveID> archiveID;
    private List<CenterID> centerID;
    private List<String> localID;
    private List<SecondaryArchiveID> secondaryArchiveID;

    @JsonProperty("ArchiveID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ArchiveID> getArchiveID() { return archiveID; }
    @JsonProperty("ArchiveID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setArchiveID(List<ArchiveID> value) { this.archiveID = value; }

    @JsonProperty("SecondaryArchiveID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<SecondaryArchiveID> getSecondaryArchiveID() { return secondaryArchiveID; }
    @JsonProperty("SecondaryArchiveID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSecondaryArchiveID(List<SecondaryArchiveID> value) { this.secondaryArchiveID = value; }

    @JsonProperty("CenterID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<CenterID> getCenterID() { return centerID; }
    @JsonProperty("CenterID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCenterID(List<CenterID> value) { this.centerID = value; }

    @JsonProperty("LocalID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getLocalID() { return localID; }
    @JsonProperty("LocalID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocalID(List<String> value) { this.localID = value; }
}
