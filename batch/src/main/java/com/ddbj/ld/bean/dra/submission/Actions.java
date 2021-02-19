package com.ddbj.ld.bean.dra.submission;

import com.fasterxml.jackson.annotation.*;

public class Actions {
    private Action action;

    @JsonProperty("ACTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Action getAction() { return action; }
    @JsonProperty("ACTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAction(Action value) { this.action = value; }
}
