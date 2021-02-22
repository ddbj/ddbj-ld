package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

public class File {
    private String path;
    private String md5;
    private String crc32;
    private String contentType;
    private String targetDBLabel;
    private Tracking tracking;
    private List<String> dataType;
    private String targetDB;
    private String targetDBContext;

    @JsonProperty("path")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPath() { return path; }
    @JsonProperty("path")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPath(String value) { this.path = value; }

    @JsonProperty("md5")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMd5() { return md5; }
    @JsonProperty("md5")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMd5(String value) { this.md5 = value; }

    @JsonProperty("crc32")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCrc32() { return crc32; }
    @JsonProperty("crc32")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCrc32(String value) { this.crc32 = value; }

    @JsonProperty("content_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContentType() { return contentType; }
    @JsonProperty("content_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContentType(String value) { this.contentType = value; }

    @JsonProperty("target_db_label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTargetDBLabel() { return targetDBLabel; }
    @JsonProperty("target_db_label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTargetDBLabel(String value) { this.targetDBLabel = value; }

    @JsonProperty("Tracking")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Tracking getTracking() { return tracking; }
    @JsonProperty("Tracking")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTracking(Tracking value) { this.tracking = value; }

    @JsonProperty("DataType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getDataType() { return dataType; }
    @JsonProperty("DataType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataType(List<String> value) { this.dataType = value; }

    @JsonProperty("target_db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTargetDB() { return targetDB; }
    @JsonProperty("target_db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTargetDB(String value) { this.targetDB = value; }

    @JsonProperty("target_db_context")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTargetDBContext() { return targetDBContext; }
    @JsonProperty("target_db_context")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTargetDBContext(String value) { this.targetDBContext = value; }
}
