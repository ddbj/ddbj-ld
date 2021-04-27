package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Provider {
    private String id;
    private String url;
    private String lotid;
    private String location;
    private String providername;
    private String preparationdate;

    @JsonProperty("Id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getId() { return id; }
    @JsonProperty("Id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setId(String value) { this.id = value; }

    @JsonProperty("Url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUrl() { return url; }
    @JsonProperty("Url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUrl(String value) { this.url = value; }

    @JsonProperty("LotId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLotId() { return lotid; }
    @JsonProperty("LotId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLotId(String value) { this.lotid = value; }

    @JsonProperty("Location")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocation() { return location; }
    @JsonProperty("Location")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocation(String value) { this.location = value; }

    @JsonProperty("provider_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getProviderName() { return providername; }
    @JsonProperty("provider_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProviderName(String value) { this.providername = value; }

    @JsonProperty("preparation_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPreparationDate() { return preparationdate; }
    @JsonProperty("preparation_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPreparationDate(String value) { this.preparationdate = value; }
}
