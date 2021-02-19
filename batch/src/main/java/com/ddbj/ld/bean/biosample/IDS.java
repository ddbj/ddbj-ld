package com.ddbj.ld.bean.biosample;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class IDS {
    private List<ID> id;

    @JsonProperty("Id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ID> getID() { return id; }
    @JsonProperty("Id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(List<ID> value) { this.id = value; }
}
