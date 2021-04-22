package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class Assembly {
    private Standard standard;
    private Custom custom;

    @JsonProperty("STANDARD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Standard getStandard() { return standard; }
    @JsonProperty("STANDARD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStandard(Standard value) { this.standard = value; }

    @JsonProperty("CUSTOM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Custom getCustom() { return custom; }
    @JsonProperty("CUSTOM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCustom(Custom value) { this.custom = value; }
}
