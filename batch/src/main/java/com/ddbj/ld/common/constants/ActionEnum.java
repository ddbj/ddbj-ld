package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActionEnum {
    GET_BIOPROJECT("getBioProject"),
    GET_BIOSAMPLE("getBioSample"),
    GET_SRA("getSRA"),
    GET_BIOPROJECT_UPDATED("getBioProjectUpdated"),
    GET_BIOSAMPLE_UPDATED("getBioSampleUpdated"),
    GET_SRA_UPDATED("getSRAUpdated"),

    REGISTER_JGA("registerJGA"),
    REGISTER_ACCESSIONS("registerAccessions"),
    REGISTER_BIOPROJECT("registerBioProject"),
    REGISTER_BIOSAMPLE("registerBioSample"),
    REGISTER_SRA("registerSRA"),

    UPDATE_JGA("updateJGA"),
    UPDATE_ACCESSIONS("updateAccessions"),
    UPDATE_BIOPROJECT("updateBioProject"),
    UPDATE_BIOSAMPLE("updateBioSample"),
    UPDATE_SRA("updateSRA"),

    VALIDATE_JGA("validateJGA"),
    VALIDATE_ACCESSIONS("validateAccessions"),
    VALIDATE_BIOPROJECT("validateBioProject"),
    VALIDATE_BIOSAMPLE("validateBioSample"),
    VALIDATE_SRA("validateSRA"),

    ;

    public final String action;
}
