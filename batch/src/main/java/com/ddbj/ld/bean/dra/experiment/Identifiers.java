package com.ddbj.ld.bean.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class Identifiers {
    private PrimaryID primaryID;
    private PrimaryID secondaryID;
    private ID externalID;
    private ID submitterID;
    private PrimaryID uuid;

    @JsonProperty("PRIMARY_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PrimaryID getPrimaryID() { return primaryID; }
    @JsonProperty("PRIMARY_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPrimaryID(PrimaryID value) { this.primaryID = value; }

    @JsonProperty("SECONDARY_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PrimaryID getSecondaryID() { return secondaryID; }
    @JsonProperty("SECONDARY_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSecondaryID(PrimaryID value) { this.secondaryID = value; }

    @JsonProperty("EXTERNAL_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ID getExternalID() { return externalID; }
    @JsonProperty("EXTERNAL_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExternalID(ID value) { this.externalID = value; }

    @JsonProperty("SUBMITTER_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ID getSubmitterID() { return submitterID; }
    @JsonProperty("SUBMITTER_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubmitterID(ID value) { this.submitterID = value; }

    @JsonProperty("UUID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PrimaryID getUUID() { return uuid; }
    @JsonProperty("UUID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUUID(PrimaryID value) { this.uuid = value; }
}
