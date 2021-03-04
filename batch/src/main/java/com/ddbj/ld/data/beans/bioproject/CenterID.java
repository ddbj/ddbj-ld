package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class CenterID {
    private String center;
    private String id;
    private String content;

    @JsonProperty("center")
<<<<<<< HEAD
    public String getCenter() { return center; }
    @JsonProperty("center")
    public void setCenter(String value) { this.center = value; }

    @JsonProperty("id")
    public String getID() { return id; }
    @JsonProperty("id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCenter() { return center; }
    @JsonProperty("center")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCenter(String value) { this.center = value; }

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getID() { return id; }
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(String value) { this.id = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setContent(String value) { this.content = value; }
}
