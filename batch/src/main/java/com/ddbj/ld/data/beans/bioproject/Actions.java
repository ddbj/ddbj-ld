package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Actions {
    private Action action;

    @JsonProperty("ACTION")
    public Action getAction() { return action; }
    @JsonProperty("ACTION")
    public void setAction(Action value) { this.action = value; }
}
