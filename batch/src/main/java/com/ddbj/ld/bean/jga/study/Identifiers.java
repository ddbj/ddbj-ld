package com.ddbj.ld.bean.jga.study;

import com.fasterxml.jackson.annotation.*;

public class Identifiers {
    private String primaryID;
    private String secondaryID;
    private ID externalID;
    private ID submitterID;
    private String uuid;

    @JsonProperty("PRIMARY_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPrimaryID() { return primaryID; }
    @JsonProperty("PRIMARY_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPrimaryID(String value) { this.primaryID = value; }

    @JsonProperty("SECONDARY_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSecondaryID() { return secondaryID; }
    @JsonProperty("SECONDARY_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSecondaryID(String value) { this.secondaryID = value; }

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
    public String getUUID() { return uuid; }
    @JsonProperty("UUID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUUID(String value) { this.uuid = value; }
}
