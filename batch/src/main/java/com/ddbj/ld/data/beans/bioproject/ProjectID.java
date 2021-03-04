package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;
<<<<<<< HEAD

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
=======
import java.util.List;

public class ProjectID {
    private List<ArchiveID> archiveID;
    private List<CenterID> centerID;
    private List<LocalID> localID;
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
    public List<LocalID> getLocalID() { return localID; }
    @JsonProperty("LocalID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocalID(List<LocalID> value) { this.localID = value; }
>>>>>>> 取り込み、修正
}
