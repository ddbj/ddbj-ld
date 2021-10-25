package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hold {
    private String releaseDate;

    @JsonProperty("release_date")
    public String getReleaseDate() { return releaseDate; }
    @JsonProperty("release_date")
    public void setReleaseDate(String value) { this.releaseDate = value; }
}
