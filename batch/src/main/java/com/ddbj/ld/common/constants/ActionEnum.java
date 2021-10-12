package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActionEnum {
    REGISTER_JGA("registerJGA"),
    REGISTER_ACCESSIONS("registerAccessions"),
    REGISTER_BIOPROJECT("registerBioProject"),
    REGISTER_BIOSAMPLE("registerBioSample"),
    REGISTER_SRA("registerSRA"),
    REGISTER_ALL("registerAll"),

    UPDATE_JGA("updateJGA"),
    UPDATE_ACCESSIONS("updateAccessions"),
    UPDATE_BIOPROJECT("updateBioProject"),
    UPDATE_BIOSAMPLE("updateBioSample"),
    UPDATE_SRA("updateSRA"),
    UPDATE_ALL("updateAll"),

    VALIDATE_JGA("validateJGA"),
    VALIDATE_ACCESSIONS("validateAccessions"),
    VALIDATE_BIOPROJECT("validateBioProject"),
    VALIDATE_BIOSAMPLE("validateBioSample"),
    VALIDATE_SRA("validateSRA"),
    VALIDATE_ALL("validateAll"),

    ;

    public final String action;
}
