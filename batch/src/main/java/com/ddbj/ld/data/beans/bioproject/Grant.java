package com.ddbj.ld.data.beans.bioproject;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.*;
=======
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
>>>>>>> 取り込み、修正

public class Grant {
    private String grantID;
    private String title;
    private Agency agency;
<<<<<<< HEAD

    @JsonProperty("GrantId")
    public String getGrantID() { return grantID; }
    @JsonProperty("GrantId")
    public void setGrantID(String value) { this.grantID = value; }

    @JsonProperty("Title")
    public String getTitle() { return title; }
    @JsonProperty("Title")
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("Agency")
    public Agency getAgency() { return agency; }
    @JsonProperty("Agency")
    public void setAgency(Agency value) { this.agency = value; }
=======
    private List<PI> pi;

    @JsonProperty("GrantId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGrantID() { return grantID; }
    @JsonProperty("GrantId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGrantID(String value) { this.grantID = value; }

    @JsonProperty("Title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTitle() { return title; }
    @JsonProperty("Title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("Agency")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Agency getAgency() { return agency; }
    @JsonProperty("Agency")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAgency(Agency value) { this.agency = value; }

    @JsonProperty("PI")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<PI> getPI() { return pi; }
    @JsonProperty("PI")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPI(List<PI> value) { this.pi = value; }
>>>>>>> 取り込み、修正
}
