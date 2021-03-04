package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;

public class Publication {
    private String id;
    private OffsetDateTime date;
<<<<<<< HEAD
    private String status;
    private String reference;
    private StructuredCitation structuredCitation;
    private String dbType;

    @JsonProperty("id")
    // FIXME ここが数値の項目が入ってくる
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
=======
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
>>>>>>> 取り込み、修正
}
