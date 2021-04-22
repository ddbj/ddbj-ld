package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class File {
    private String filename;
    private String filetype;
    private String checksumMethod;
    private String checksum;

    @JsonProperty("filename")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFilename() { return filename; }
    @JsonProperty("filename")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setFilename(String value) { this.filename = value; }

    @JsonProperty("filetype")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFiletype() { return filetype; }
    @JsonProperty("filetype")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setFiletype(String value) { this.filetype = value; }

    @JsonProperty("checksum_method")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getChecksumMethod() { return checksumMethod; }
    @JsonProperty("checksum_method")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setChecksumMethod(String value) { this.checksumMethod = value; }

    @JsonProperty("checksum")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getChecksum() { return checksum; }
    @JsonProperty("checksum")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setChecksum(String value) { this.checksum = value; }
}
