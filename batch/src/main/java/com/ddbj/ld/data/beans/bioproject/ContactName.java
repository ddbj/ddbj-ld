package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactName {
    private String first;
    private String last;
    private String middle;

    @JsonProperty("First")
    public String getFirst() { return first; }
    @JsonProperty("First")
    public void setFirst(String value) { this.first = value; }

    @JsonProperty("Last")
    public String getLast() { return last; }
    @JsonProperty("Last")
    public void setLast(String value) { this.last = value; }

    @JsonProperty("Middle")
    public String getMiddle() { return middle; }
    @JsonProperty("Middle")
    public void setMiddle(String value) { this.middle = value; }
}
