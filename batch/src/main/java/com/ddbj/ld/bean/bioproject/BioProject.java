package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class BioProject {
    private Package bioProjectPackage;

    @JsonProperty("Package")
    public Package getBioProjectPackage() { return bioProjectPackage; }
    @JsonProperty("Package")
    public void setBioProjectPackage(Package value) { this.bioProjectPackage = value; }
}
