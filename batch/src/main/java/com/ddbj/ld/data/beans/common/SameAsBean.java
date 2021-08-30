package com.ddbj.ld.data.beans.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SameAsBean {
    private String identifier;

    private String type;

    private String url;
}
