package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class Publication {
    private String id;
    private OffsetDateTime date;
    private String status;
    private String reference;
    private StructuredCitation structuredCitation;
    private String dbType;

    @JsonProperty("id")
    public String getID() { return id; }
    @JsonProperty("id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("date")
    public OffsetDateTime getDate() { return date; }
    @JsonProperty("date")
    public void setDate(OffsetDateTime value) { this.date = value; }

    @JsonProperty("status")
    public String getStatus() { return status; }
    @JsonProperty("status")
    public void setStatus(String value) { this.status = value; }

    @JsonProperty("Reference")
    public String getReference() { return reference; }
    @JsonProperty("Reference")
    public void setReference(String value) { this.reference = value; }

    @JsonProperty("StructuredCitation")
    public StructuredCitation getStructuredCitation() { return structuredCitation; }
    @JsonProperty("StructuredCitation")
    public void setStructuredCitation(StructuredCitation value) { this.structuredCitation = value; }

    @JsonProperty("DbType")
    public String getDBType() { return dbType; }
    @JsonProperty("DbType")
    public void setDBType(String value) { this.dbType = value; }
}
