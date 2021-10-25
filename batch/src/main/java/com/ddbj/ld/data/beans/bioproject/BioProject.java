package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BioProject {
    private Package bioProjectPackage;

    @JsonProperty("Package")
    public Package getBioProjectPackage() { return bioProjectPackage; }
    @JsonProperty("Package")
    public void setBioProjectPackage(Package value) { this.bioProjectPackage = value; }

}
