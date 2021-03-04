package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Size {
    private String units;
    private String content;

    @JsonProperty("units")
<<<<<<< HEAD
    public String getUnits() { return units; }
    @JsonProperty("units")
    public void setUnits(String value) { this.units = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUnits() { return units; }
    @JsonProperty("units")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUnits(String value) { this.units = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setContent(String value) { this.content = value; }
}
