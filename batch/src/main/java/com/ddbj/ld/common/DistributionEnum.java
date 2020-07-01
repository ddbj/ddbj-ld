package com.ddbj.ld.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DistributionEnum {
    TYPE_DATA_DOWNLOAD("DataDownload"),
    ENCODING_FORMAT_JSON("JSON"),
    ENCODING_FORMAT_JSON_LD("JSON-LD"),
    CONTACT_URL_EXTENTION_JSON(".json"),
    CONTACT_URL_EXTENTION_JSON_LD(".jsonld");

    private final String item;

    public static DistributionEnum getItem(String item) {
        DistributionEnum[] items = DistributionEnum.values();
        for (DistributionEnum typeEnum : items) {
            if (typeEnum.toString().equals(item)) {
                return typeEnum;
            }
        }
        return null;
    }
}
