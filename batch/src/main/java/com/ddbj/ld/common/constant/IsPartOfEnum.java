package com.ddbj.ld.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IsPartOfEnum {
    DRA("dra"),
    JGA("jga");

    private final String isPartOf;

    public static IsPartOfEnum getIsPartOf(String isPartOf) {
        IsPartOfEnum[] isPartOfs = IsPartOfEnum.values();
        for (IsPartOfEnum isPartOfEnum : isPartOfs) {
            if (isPartOfEnum.toString().equals(isPartOf)) {
                return isPartOfEnum;
            }
        }
        return null;
    }
}