package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

// DDBJのSubmission.xsdから生成したクラス
public class DataSubmission {
    private String name;
    private String dataModel;
    private String contentType;
    private String contentEncoding;
    private String targetDBLabel;
    private Tracking tracking;
    private XMLContent xmlContent;
    private String dataContent;

    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() { return name; }
    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(String value) { this.name = value; }

    @JsonProperty("data_model")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDataModel() { return dataModel; }
    @JsonProperty("data_model")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataModel(String value) { this.dataModel = value; }

    @JsonProperty("content_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContentType() { return contentType; }
    @JsonProperty("content_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContentType(String value) { this.contentType = value; }

    @JsonProperty("content_encoding")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContentEncoding() { return contentEncoding; }
    @JsonProperty("content_encoding")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContentEncoding(String value) { this.contentEncoding = value; }

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

    @JsonProperty("XmlContent")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public XMLContent getXMLContent() { return xmlContent; }
    @JsonProperty("XmlContent")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setXMLContent(XMLContent value) { this.xmlContent = value; }

    @JsonProperty("DataContent")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDataContent() { return dataContent; }
    @JsonProperty("DataContent")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDataContent(String value) { this.dataContent = value; }
}