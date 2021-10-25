package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Add {
    private String source;
    private String schema;

    @JsonProperty("source")
    public String getSource() { return source; }
    @JsonProperty("source")
    public void setSource(String value) { this.source = value; }

    @JsonProperty("schema")
    public String getSchema() { return schema; }
    @JsonProperty("schema")
    public void setSchema(String value) { this.schema = value; }
}
