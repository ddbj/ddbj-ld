package com.ddbj.ld.bean.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class XrefLink {
    private Title db;
    private Title id;
    private Title label;

    @JsonProperty("DB")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getDB() { return db; }
    @JsonProperty("DB")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDB(Title value) { this.db = value; }

    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getID() { return id; }
    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(Title value) { this.id = value; }

    @JsonProperty("LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getLabel() { return label; }
    @JsonProperty("LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(Title value) { this.label = value; }
}
