package com.ddbj.ld.data.beans.jga.study;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;

public class Study implements IPropertiesBean {
    private STUDYClass study;

    @JsonProperty("STUDY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public STUDYClass getStudy() { return study; }
    @JsonProperty("STUDY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudy(STUDYClass value) { this.study = value; }
}
