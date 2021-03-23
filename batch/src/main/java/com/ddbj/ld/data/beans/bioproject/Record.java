package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Record {
    private String lastUpdate;
    private String status;
    private String errorMessage;
    private Target target;

    @JsonProperty("last_update")
    public String getLastUpdate() { return lastUpdate; }
    @JsonProperty("last_update")
    public void setLastUpdate(String value) { this.lastUpdate = value; }

    @JsonProperty("status")
    public String getStatus() { return status; }
    @JsonProperty("status")
    public void setStatus(String value) { this.status = value; }

    @JsonProperty("error_message")
    public String getErrorMessage() { return errorMessage; }
    @JsonProperty("error_message")
    public void setErrorMessage(String value) { this.errorMessage = value; }

    @JsonProperty("Target")
    public Target getTarget() { return target; }
    @JsonProperty("Target")
    public void setTarget(Target value) { this.target = value; }
}
