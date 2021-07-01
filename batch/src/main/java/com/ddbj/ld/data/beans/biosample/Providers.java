package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Providers {
    Provider provider;

    @JsonProperty("Provider")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Provider getProvider() { return provider; }
    @JsonProperty("Provider")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProvider(Provider value) { this.provider = value; }

}
