package com.ddbj.ld.bean.jga.study;

import com.fasterxml.jackson.annotation.*;

public class Study {
    private STUDYClass study;

    @JsonProperty("STUDY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public STUDYClass getStudy() { return study; }
    @JsonProperty("STUDY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudy(STUDYClass value) { this.study = value; }
}
