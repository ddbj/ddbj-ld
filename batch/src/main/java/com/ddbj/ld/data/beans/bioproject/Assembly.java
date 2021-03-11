package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Assembly {
    private String assemblyName;
    private String assemblyAccession;
    private String wgSprefix;
    private String locusTagPrefix;
    private Replicon replicon;

    @JsonProperty("assemblyName")
    public String getAssemblyName() { return assemblyName; }
    @JsonProperty("assemblyName")
    public void setAssemblyName(String value) { this.assemblyName = value; }

    @JsonProperty("assemblyAccession")
    public String getAssemblyAccession() { return assemblyAccession; }
    @JsonProperty("assemblyAccession")
    public void setAssemblyAccession(String value) { this.assemblyAccession = value; }

    @JsonProperty("WGSprefix")
    public String getWgSprefix() { return wgSprefix; }
    @JsonProperty("WGSprefix")
    public void setWgSprefix(String value) { this.wgSprefix = value; }

    @JsonProperty("LocusTagPrefix")
    public String getLocusTagPrefix() { return locusTagPrefix; }
    @JsonProperty("LocusTagPrefix")
    public void setLocusTagPrefix(String value) { this.locusTagPrefix = value; }

    @JsonProperty("Replicon")
    public Replicon getReplicon() { return replicon; }
    @JsonProperty("Replicon")
    public void setReplicon(Replicon value) { this.replicon = value; }
}
