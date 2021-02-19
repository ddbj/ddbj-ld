package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class DataBlock {
    private String name;
    private String serial;
    private String member;
    private Files files;

    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() { return name; }
    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(String value) { this.name = value; }

    @JsonProperty("serial")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSerial() { return serial; }
    @JsonProperty("serial")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSerial(String value) { this.serial = value; }

    @JsonProperty("member")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMember() { return member; }
    @JsonProperty("member")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMember(String value) { this.member = value; }

    @JsonProperty("FILES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Files getFiles() { return files; }
    @JsonProperty("FILES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setFiles(Files value) { this.files = value; }
}
