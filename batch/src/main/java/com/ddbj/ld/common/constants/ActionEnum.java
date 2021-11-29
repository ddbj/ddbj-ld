package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActionEnum {
    GET_BIOPROJECT("getBioProject"),
    GET_BIOSAMPLE("getBioSample"),
    GET_SRA("getSRA"),
    GET_SRA_UPDATED("getSRAUpdated"),

    REGISTER_SRA_ACCESSIONS("registerSRAAccessions"),
    REGISTER_DRA_ACCESSIONS("registerDRAAccessions"),

    REGISTER_JGA("registerJGA"),
    REGISTER_BIOPROJECT("registerBioProject"),
    REGISTER_DDBJ_BIOPROJECT("registerDDBJBioProject"),
    REGISTER_BIOSAMPLE("registerBioSample"),
    REGISTER_DDBJ_BIOSAMPLE("registerDDBJBioSample"),
    REGISTER_SRA("registerSRA"),
    REGISTER_DRA("registerDRA"),

    UPDATE_SRA_ACCESSIONS("updateSRAAccessions"),
    UPDATE_DRA_ACCESSIONS("updateDRAAccessions"),
    UPDATE_BIOPROJECT("updateBioProject"),
    UPDATE_DDBJ_BIOPROJECT("updateDDBJBioProject"),
    UPDATE_BIOSAMPLE("updateBioSample"),
    UPDATE_DDBJ_BIOSAMPLE("updateDDBJBioSample"),
    UPDATE_SRA("updateSRA"),
    UPDATE_DRA("updateDRA"),

    VALIDATE_JGA("validateJGA"),
    VALIDATE_BIOPROJECT("validateBioProject"),
    VALIDATE_DDBJ_BIOPROJECT("validateDDBJBioProject"),
    VALIDATE_BIOSAMPLE("validateBioSample"),
    VALIDATE_DDBJ_BIOSAMPLE("validateDDBJBioSample"),
    VALIDATE_SRA("validateSRA"),
    VALIDATE_DRA("validateDRA"),

    ;

    public final String action;
}
