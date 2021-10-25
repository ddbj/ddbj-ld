package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Identifiers {
    private PrimaryID primaryID;
    private PrimaryID secondaryID;
    private ID externalID;
    private ID submitterID;
    private PrimaryID uuid;

    @JsonProperty("PRIMARY_ID")
    public PrimaryID getPrimaryID() { return primaryID; }
    @JsonProperty("PRIMARY_ID")
    public void setPrimaryID(PrimaryID value) { this.primaryID = value; }

    @JsonProperty("SECONDARY_ID")
    public PrimaryID getSecondaryID() { return secondaryID; }
    @JsonProperty("SECONDARY_ID")
    public void setSecondaryID(PrimaryID value) { this.secondaryID = value; }

    @JsonProperty("EXTERNAL_ID")
    public ID getExternalID() { return externalID; }
    @JsonProperty("EXTERNAL_ID")
    public void setExternalID(ID value) { this.externalID = value; }

    @JsonProperty("SUBMITTER_ID")
    public ID getSubmitterID() { return submitterID; }
    @JsonProperty("SUBMITTER_ID")
    public void setSubmitterID(ID value) { this.submitterID = value; }

    @JsonProperty("UUID")
    public PrimaryID getUUID() { return uuid; }
    @JsonProperty("UUID")
    public void setUUID(PrimaryID value) { this.uuid = value; }
}
