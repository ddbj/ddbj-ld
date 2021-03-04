package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;

public class Publication {
    private String id;
    private OffsetDateTime date;
    private String reference;
    private String dbType;
    // FIXME 要整理、DDBJにもNCBIにもない属性の情報
    private String status;
    private StructuredCitation structuredCitation;

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getID() { return id; }
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(String value) { this.id = value; }

    @JsonProperty("date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public OffsetDateTime getDate() { return date; }
    @JsonProperty("date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDate(OffsetDateTime value) { this.date = value; }

    @JsonProperty("Reference")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReference() { return reference; }
    @JsonProperty("Reference")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReference(String value) { this.reference = value; }

    @JsonProperty("DbType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDBType() { return dbType; }
    @JsonProperty("DbType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDBType(String value) { this.dbType = value; }

    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStatus() { return status; }
    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStatus(String value) { this.status = status; }

    @JsonProperty("StructuredCitation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StructuredCitation getStructuredCitation() { return structuredCitation; }
    @JsonProperty("StructuredCitation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStructuredCitation(StructuredCitation value) { this.structuredCitation = structuredCitation; }
}
