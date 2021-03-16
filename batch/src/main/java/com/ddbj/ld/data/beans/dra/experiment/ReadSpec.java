package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class ReadSpec {
    private String readIndex;
    private String readLabel;
    private String readClass;
    private String readType;
    private RelativeOrder relativeOrder;
    private String baseCoord;
    private ExpectedBasecallTable expectedBasecallTable;

    @JsonProperty("READ_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadIndex() { return readIndex; }
    @JsonProperty("READ_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadIndex(String value) { this.readIndex = value; }

    @JsonProperty("READ_LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadLabel() { return readLabel; }
    @JsonProperty("READ_LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadLabel(String value) { this.readLabel = value; }

    @JsonProperty("READ_CLASS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadClass() { return readClass; }
    @JsonProperty("READ_CLASS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadClass(String value) { this.readClass = value; }

    @JsonProperty("READ_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadType() { return readType; }
    @JsonProperty("READ_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadType(String value) { this.readType = value; }

    @JsonProperty("RELATIVE_ORDER")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RelativeOrder getRelativeOrder() { return relativeOrder; }
    @JsonProperty("RELATIVE_ORDER")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRelativeOrder(RelativeOrder value) { this.relativeOrder = value; }

    @JsonProperty("BASE_COORD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBaseCoord() { return baseCoord; }
    @JsonProperty("BASE_COORD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBaseCoord(String value) { this.baseCoord = value; }

    @JsonProperty("EXPECTED_BASECALL_TABLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ExpectedBasecallTable getExpectedBasecallTable() { return expectedBasecallTable; }
    @JsonProperty("EXPECTED_BASECALL_TABLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExpectedBasecallTable(ExpectedBasecallTable value) { this.expectedBasecallTable = value; }
}
