package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ContactName {
    private String first;
    private String last;
    private String middle;

    @JsonProperty("First")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFirst() { return first; }
    @JsonProperty("First")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setFirst(String value) { this.first = value; }

    @JsonProperty("Last")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLast() { return last; }
    @JsonProperty("Last")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLast(String value) { this.last = value; }

    @JsonProperty("Middle")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMiddle() { return middle; }
    @JsonProperty("Middle")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMiddle(String value) { this.middle = value; }
}