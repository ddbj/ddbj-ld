package com.ddbj.ld.data.beans.dra.submission;

import com.fasterxml.jackson.annotation.*;

public class Contact {
    private String name;
    private String informOnStatus;
    private String informOnError;

    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() { return name; }
    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(String value) { this.name = value; }

    @JsonProperty("inform_on_status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getInformOnStatus() { return informOnStatus; }
    @JsonProperty("inform_on_status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setInformOnStatus(String value) { this.informOnStatus = value; }

    @JsonProperty("inform_on_error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getInformOnError() { return informOnError; }
    @JsonProperty("inform_on_error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setInformOnError(String value) { this.informOnError = value; }
}
