package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class LibraryLayout {
    private String single;
    private Paired paired;

    @JsonProperty("SINGLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSingle() { return single; }
    @JsonProperty("SINGLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSingle(String value) { this.single = value; }

    @JsonProperty("PAIRED")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Paired getPaired() { return paired; }
    @JsonProperty("PAIRED")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPaired(Paired value) { this.paired = value; }
}
