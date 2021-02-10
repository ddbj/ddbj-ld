package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Hold {
    private String releaseDate;

    @JsonProperty("release_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReleaseDate() { return releaseDate; }
    @JsonProperty("release_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReleaseDate(String value) { this.releaseDate = value; }
}
