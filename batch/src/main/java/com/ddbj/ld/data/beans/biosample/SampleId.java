package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SampleId {
    @XmlAttribute(name = "is_primary")
    @JsonProperty("is_primary")
    private String isPrimary;

    @XmlAttribute(name = "namespace")
    @JsonProperty("namespace")
    private String namespace;

    @XmlAttribute(name = "other_namespace")
    @JsonProperty("other_namespace")
    private String otherNamespace;

    @XmlAttribute(name = "db")
    @JsonProperty("db")
    private String db; // 関連リソースへのリンク. 格納

    @XmlAttribute(name = "db_label")
    @JsonProperty("db_label")
    private String dblabel; // 関連リソースへのリンク. 格納

    @XmlAttribute(name = "is_hidden")
    @JsonProperty("is_hidden")
    private String ishidden; // アクセス制限サンプル. suppressed のフラグ? 格納

    @XmlValue
    @JsonProperty("content")
    private String content;
}
