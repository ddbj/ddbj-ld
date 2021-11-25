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

    REGISTER_SRA_ACCESSIONS("registerSraAccessions"),
    REGISTER_DRA_ACCESSIONS("registerDraAccessions"),

    REGISTER_JGA("registerJGA"),
    REGISTER_BIOPROJECT("registerBioProject"),
    REGISTER_DDBJ_BIOPROJECT("registerDdbjBioProject"),
    REGISTER_BIOSAMPLE("registerBioSample"),
    REGISTER_DDBJ_BIOSAMPLE("registerDdbjBioSample"),
    REGISTER_SRA("registerSRA"),
    REGISTER_DRA("registerDRA"),

    UPDATE_SRA_ACCESSIONS("updateSraAccessions"),
    UPDATE_DRA_ACCESSIONS("updateDraAccessions"),
    UPDATE_BIOPROJECT("updateBioProject"),
    UPDATE_DDBJ_BIOPROJECT("updateDdbjBioProject"),
    UPDATE_BIOSAMPLE("updateBioSample"),
    UPDATE_DDBJ_BIOSAMPLE("updateDdbjBioSample"),
    UPDATE_SRA("updateSRA"),
    UPDATE_DRA("updateDRA"),

    VALIDATE_JGA("validateJGA"),
    VALIDATE_BIOPROJECT("validateBioProject"),
    VALIDATE_DDBJ_BIOPROJECT("validateDdbjBioProject"),
    VALIDATE_BIOSAMPLE("validateBioSample"),
    VALIDATE_DDBJ_BIOSAMPLE("validateBioSample"),
    VALIDATE_SRA("validateSRA"),
    VALIDATE_DRA("validateDRA"),

    ;

    public final String action;
}
