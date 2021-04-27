package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;

public class SampleId {
    private String namespace;
    private String otherNamespace;
    private String isPrimary;
    private String content;
    private String db; // FIXME
    private String dblabel; // FIXME

    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getNamespace() { return namespace; }
    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setNamespace(String value) { this.namespace = value; }

    @JsonProperty("other_namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOtherNamespace() { return otherNamespace; }
    @JsonProperty("other_namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOtherNamespace(String value) { this.otherNamespace = value; }

    @JsonProperty("is_primary")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIsPrimary() { return isPrimary; }
    @JsonProperty("is_primary")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIsPrimary(String value) { this.isPrimary = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }

    // TODO: 実データに存在するがXSDに定義が無いため要仕様確認
    @JsonProperty("db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDB() { return db; }
    @JsonProperty("db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDB(String value) { this.db = value; }

    // TODO: 実データに存在するがXSDに定義が無いため要仕様確認
    @JsonProperty("db_label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDBLabel() { return dblabel; }
    @JsonProperty("db_label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDBLabel(String value) { this.dblabel = value; }
}