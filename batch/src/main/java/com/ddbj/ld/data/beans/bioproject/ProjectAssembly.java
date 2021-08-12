package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectAssembly {
    private String typeOfPairing;
    private Assembly assembly;
    private ArchiveID projectIDRef;

    @JsonProperty("typeOfPairing")
    public String getTypeOfPairing() { return typeOfPairing; }
    @JsonProperty("typeOfPairing")
    public void setTypeOfPairing(String value) { this.typeOfPairing = value; }

    @JsonProperty("Assembly")
    public Assembly getAssembly() { return assembly; }
    @JsonProperty("Assembly")
    public void setAssembly(Assembly value) { this.assembly = value; }

    @JsonProperty("ProjectIDRef")
    public ArchiveID getProjectIDRef() { return projectIDRef; }
    @JsonProperty("ProjectIDRef")
    public void setProjectIDRef(ArchiveID value) { this.projectIDRef = value; }
}
