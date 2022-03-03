package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BioProject {
    @JsonProperty("Package")
    private Package bioProjectPackage;
}
