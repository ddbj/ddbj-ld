package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Record {
    private String lastUpdate;
    private String status;
    private String errorMessage;
    private Target target;

    @JsonProperty("last_update")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLastUpdate() { return lastUpdate; }
    @JsonProperty("last_update")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLastUpdate(String value) { this.lastUpdate = value; }

    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStatus() { return status; }
    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStatus(String value) { this.status = value; }

    @JsonProperty("error_message")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getErrorMessage() { return errorMessage; }
    @JsonProperty("error_message")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setErrorMessage(String value) { this.errorMessage = value; }

    @JsonProperty("Target")
    public Target getTarget() { return target; }
    @JsonProperty("Target")
    public void setTarget(Target value) { this.target = value; }
}
