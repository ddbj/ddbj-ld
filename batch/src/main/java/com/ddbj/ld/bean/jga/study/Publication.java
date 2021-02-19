package com.ddbj.ld.bean.jga.study;

import com.fasterxml.jackson.annotation.*;
import java.time.LocalDate;

public class Publication {
    private String id;
    private LocalDate date;
    private String status;
    private String reference;
    private StructuredCitation structuredCitation;
    private String dbType;

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getID() { return id; }
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(String value) { this.id = value; }

    @JsonProperty("date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public LocalDate getDate() { return date; }
    @JsonProperty("date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDate(LocalDate value) { this.date = value; }

    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStatus() { return status; }
    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStatus(String value) { this.status = value; }

    @JsonProperty("REFERENCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReference() { return reference; }
    @JsonProperty("REFERENCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReference(String value) { this.reference = value; }

    @JsonProperty("STRUCTURED_CITATION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StructuredCitation getStructuredCitation() { return structuredCitation; }
    @JsonProperty("STRUCTURED_CITATION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStructuredCitation(StructuredCitation value) { this.structuredCitation = value; }

    @JsonProperty("DB_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDBType() { return dbType; }
    @JsonProperty("DB_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDBType(String value) { this.dbType = value; }
}
