package com.ddbj.ld.bean;

import com.ddbj.ld.common.TypeEnum;
import lombok.Data;

@Data
public class DBXrefsBean {
    // TODO nameかtypeは消す
    private String name;

    private String identifier;

    private TypeEnum type;
}
