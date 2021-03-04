package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Name {
    private String first;
    private String last;
    private String middle;
    private String suffix;

    @JsonProperty("First")
<<<<<<< HEAD
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

    @JsonProperty("Suffix")
    public String getSuffix() { return suffix; }
    @JsonProperty("Suffix")
    public void setSuffix(String value) { this.suffix = value; }
}
=======
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

    @JsonProperty("Suffix")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSuffix() { return suffix; }
    @JsonProperty("Suffix")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSuffix(String value) { this.suffix = value; }
}
>>>>>>> 取り込み、修正
