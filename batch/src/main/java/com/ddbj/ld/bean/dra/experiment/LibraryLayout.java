package com.ddbj.ld.bean.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class LibraryLayout {
    private Paired paired;

    @JsonProperty("PAIRED")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Paired getPaired() { return paired; }
    @JsonProperty("PAIRED")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPaired(Paired value) { this.paired = value; }
}
