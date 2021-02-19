package com.ddbj.ld.bean.jga.study;

import com.fasterxml.jackson.annotation.*;

public class Name {
    private String first;
    private String last;
    private String middle;
    private String suffix;

    @JsonProperty("FIRST")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFirst() { return first; }
    @JsonProperty("FIRST")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setFirst(String value) { this.first = value; }

    @JsonProperty("LAST")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLast() { return last; }
    @JsonProperty("LAST")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLast(String value) { this.last = value; }

    @JsonProperty("MIDDLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMiddle() { return middle; }
    @JsonProperty("MIDDLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMiddle(String value) { this.middle = value; }

    @JsonProperty("SUFFIX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSuffix() { return suffix; }
    @JsonProperty("SUFFIX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSuffix(String value) { this.suffix = value; }
}
