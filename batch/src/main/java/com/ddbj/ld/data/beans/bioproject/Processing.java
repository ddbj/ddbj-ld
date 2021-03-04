package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Processing {
    private String owner;
    private String id;
    private String action;

    @JsonProperty("owner")
<<<<<<< HEAD
    public String getOwner() { return owner; }
    @JsonProperty("owner")
    public void setOwner(String value) { this.owner = value; }

    @JsonProperty("id")
    public String getID() { return id; }
    @JsonProperty("id")
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOwner() { return owner; }
    @JsonProperty("owner")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOwner(String value) { this.owner = value; }

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getID() { return id; }
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setID(String value) { this.id = value; }

    @JsonProperty("action")
    public String getAction() { return action; }
    @JsonProperty("action")
    public void setAction(String value) { this.action = value; }
}
