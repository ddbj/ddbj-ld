package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrganismEnum {
    HOMO_SAPIENS_NAME("Homo sapiens"),
    HOMO_SAPIENS_IDENTIFIER("9606");

    public final String item;

    public static OrganismEnum getItem(String item) {
        OrganismEnum[] Items = OrganismEnum.values();
        for (OrganismEnum organismEnum : Items) {
            if (organismEnum.toString().equals(item)) {
                return organismEnum;
            }
        }
        return null;
    }
}
