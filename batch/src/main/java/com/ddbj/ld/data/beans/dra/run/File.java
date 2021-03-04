package com.ddbj.ld.data.beans.dra.run;

import com.fasterxml.jackson.annotation.*;

public class File {
    private String filename;
    private String filetype;
    private String qualityScoringSystem;
    private String qualityEncoding;
    private String asciiOffset;
    private String checksumMethod;
    private String checksum;
    private String readLabel;

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

    @JsonProperty("quality_scoring_system")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getQualityScoringSystem() { return qualityScoringSystem; }
    @JsonProperty("quality_scoring_system")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setQualityScoringSystem(String value) { this.qualityScoringSystem = value; }

    @JsonProperty("quality_encoding")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getQualityEncoding() { return qualityEncoding; }
    @JsonProperty("quality_encoding")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setQualityEncoding(String value) { this.qualityEncoding = value; }

    @JsonProperty("ascii_offset")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getASCIIOffset() { return asciiOffset; }
    @JsonProperty("ascii_offset")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setASCIIOffset(String value) { this.asciiOffset = value; }

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

    @JsonProperty("READ_LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadLabel() { return readLabel; }
    @JsonProperty("READ_LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadLabel(String value) { this.readLabel = value; }
}
