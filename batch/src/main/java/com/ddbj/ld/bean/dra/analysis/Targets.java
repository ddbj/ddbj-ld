package com.ddbj.ld.bean.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class Targets {
    private StudyRef target;
    private Identifiers identifiers;

    @JsonProperty("TARGET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyRef getTarget() { return target; }
    @JsonProperty("TARGET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTarget(StudyRef value) { this.target = value; }

    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Identifiers getIdentifiers() { return identifiers; }
    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIdentifiers(Identifiers value) { this.identifiers = value; }
}
