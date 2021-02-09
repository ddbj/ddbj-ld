package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;

public class Publication {
    private String id;
    private OffsetDateTime date;
    private String reference;
    private String dbType;

    @JsonProperty("id")
    public String getID() { return id; }
    @JsonProperty("id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("date")
    public OffsetDateTime getDate() { return date; }
    @JsonProperty("date")
    public void setDate(OffsetDateTime value) { this.date = value; }

    @JsonProperty("Reference")
    public String getReference() { return reference; }
    @JsonProperty("Reference")
    public void setReference(String value) { this.reference = value; }

    @JsonProperty("DbType")
    public String getDBType() { return dbType; }
    @JsonProperty("DbType")
    public void setDBType(String value) { this.dbType = value; }
}
