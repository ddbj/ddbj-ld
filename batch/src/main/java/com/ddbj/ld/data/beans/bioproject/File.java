package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class File {
    private String path;
    private String md5;
    private String crc32;
    private String contentType;
    private String targetDBLabel;
    private Tracking tracking;
    private String dataType;
    private String targetDB;
    private String targetDBContext;

    @JsonProperty("path")
    public String getPath() { return path; }
    @JsonProperty("path")
    public void setPath(String value) { this.path = value; }

    @JsonProperty("md5")
    public String getMd5() { return md5; }
    @JsonProperty("md5")
    public void setMd5(String value) { this.md5 = value; }

    @JsonProperty("crc32")
    public String getCrc32() { return crc32; }
    @JsonProperty("crc32")
    public void setCrc32(String value) { this.crc32 = value; }

    @JsonProperty("content_type")
    public String getContentType() { return contentType; }
    @JsonProperty("content_type")
    public void setContentType(String value) { this.contentType = value; }

    @JsonProperty("target_db_label")
    public String getTargetDBLabel() { return targetDBLabel; }
    @JsonProperty("target_db_label")
    public void setTargetDBLabel(String value) { this.targetDBLabel = value; }

    @JsonProperty("Tracking")
    public Tracking getTracking() { return tracking; }
    @JsonProperty("Tracking")
    public void setTracking(Tracking value) { this.tracking = value; }

    @JsonProperty("DataType")
    public String getDataType() { return dataType; }
    @JsonProperty("DataType")
    public void setDataType(String value) { this.dataType = value; }

    @JsonProperty("target_db")
    public String getTargetDB() { return targetDB; }
    @JsonProperty("target_db")
    public void setTargetDB(String value) { this.targetDB = value; }

    @JsonProperty("target_db_context")
    public String getTargetDBContext() { return targetDBContext; }
    @JsonProperty("target_db_context")
    public void setTargetDBContext(String value) { this.targetDBContext = value; }
}
