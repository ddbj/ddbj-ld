package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PI {
    private String affil;
    private String last;
    private String auth;
    private Integer grantUserId;
    private String first;

    @JsonProperty("affil")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAffil() { return affil; }
    @JsonProperty("affil")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAffil(String value) { this.affil = value; }
    @JsonProperty("Last")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLast() { return last; }
    @JsonProperty("Last")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLast(String value) { this.last = value; }
    @JsonProperty("auth")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAuth() { return auth; }
    @JsonProperty("auth")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAuth(String value) { this.auth = value; }
    @JsonProperty("grant_user_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getGrantUserId() { return grantUserId; }
    @JsonProperty("grant_user_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGrantUserId(Integer value) { this.grantUserId = value; }
    @JsonProperty("First")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFirst() { return first; }
    @JsonProperty("First")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setFirst(String value) { this.first = value; }
}
