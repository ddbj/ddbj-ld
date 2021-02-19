package com.ddbj.ld.bean.biosample;

import com.fasterxml.jackson.annotation.*;

public class Table {
    private String caption;
    private Header header;
    private Body body;

    @JsonProperty("Caption")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCaption() { return caption; }
    @JsonProperty("Caption")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCaption(String value) { this.caption = value; }

    @JsonProperty("Header")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Header getHeader() { return header; }
    @JsonProperty("Header")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setHeader(Header value) { this.header = value; }

    @JsonProperty("Body")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Body getBody() { return body; }
    @JsonProperty("Body")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBody(Body value) { this.body = value; }
}
