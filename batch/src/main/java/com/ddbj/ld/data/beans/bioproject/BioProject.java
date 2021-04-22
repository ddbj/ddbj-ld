package com.ddbj.ld.data.beans.bioproject;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;

public class BioProject implements IPropertiesBean {
    private Package bioProjectPackage;

    @JsonProperty("Package")
    public Package getBioProjectPackage() { return bioProjectPackage; }
    @JsonProperty("Package")
    public void setBioProjectPackage(Package value) { this.bioProjectPackage = value; }
}
