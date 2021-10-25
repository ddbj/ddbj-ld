package com.ddbj.ld.data.beans.sra.submission;

import com.fasterxml.jackson.annotation.*;

public class Add {
    private String source;
    private String schema;

    @JsonProperty("source")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSource() { return source; }
    @JsonProperty("source")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSource(String value) { this.source = value; }

    @JsonProperty("schema")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSchema() { return schema; }
    @JsonProperty("schema")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSchema(String value) { this.schema = value; }
}
