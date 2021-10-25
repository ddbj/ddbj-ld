package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
    private String name;
    private String dataModel;
    private String contentType;
    private String contentEncoding;
    private String targetDBLabel;
    private Tracking tracking;
    private XMLContent xmlContent;
    private String dataContent;

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("data_model")
    public String getDataModel() { return dataModel; }
    @JsonProperty("data_model")
    public void setDataModel(String value) { this.dataModel = value; }

    @JsonProperty("content_type")
    public String getContentType() { return contentType; }
    @JsonProperty("content_type")
    public void setContentType(String value) { this.contentType = value; }

    @JsonProperty("content_encoding")
    public String getContentEncoding() { return contentEncoding; }
    @JsonProperty("content_encoding")
    public void setContentEncoding(String value) { this.contentEncoding = value; }

    @JsonProperty("target_db_label")
    public String getTargetDBLabel() { return targetDBLabel; }
    @JsonProperty("target_db_label")
    public void setTargetDBLabel(String value) { this.targetDBLabel = value; }

    @JsonProperty("Tracking")
    public Tracking getTracking() { return tracking; }
    @JsonProperty("Tracking")
    public void setTracking(Tracking value) { this.tracking = value; }

    @JsonProperty("XmlContent")
    public XMLContent getXMLContent() { return xmlContent; }
    @JsonProperty("XmlContent")
    public void setXMLContent(XMLContent value) { this.xmlContent = value; }

    @JsonProperty("DataContent")
    public String getDataContent() { return dataContent; }
    @JsonProperty("DataContent")
    public void setDataContent(String value) { this.dataContent = value; }
}
