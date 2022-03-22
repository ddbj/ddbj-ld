package com.ddbj.ld.data.beans.biosample;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.ddbj.ld.data.beans.common.OffsetDateTimeAdapter;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.OffsetDateTime;

@XmlRootElement(name = "BioSample") // XMLのルートタグ
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true) // NCBI Entrez 検索用の整数 ID. skip
@Data
public class BioSampleClass implements IPropertiesBean {
    @XmlAttribute(name = "access")
    @JsonProperty("access")
    private String access;

    @XmlAttribute(name = "publication_date")
    @XmlJavaTypeAdapter(OffsetDateTimeAdapter.class)
    @JsonProperty("publication_date")
    private OffsetDateTime publicationDate;

    @XmlAttribute(name = "last_update")
    @XmlJavaTypeAdapter(OffsetDateTimeAdapter.class)
    @JsonProperty("last_update")
    private OffsetDateTime lastUpdate;

    @XmlAttribute(name = "submission_date")
    @XmlJavaTypeAdapter(OffsetDateTimeAdapter.class)
    @JsonProperty("submission_date")
    private OffsetDateTime submissionDate; // 登録日. 重要なので格納

    @XmlAttribute(name = "accession")
    @JsonProperty("accession")
    private String accession; // アクセッション番号. 重要なので格納

    @XmlElement(name = "Ids")
    @JsonProperty("Ids")
    private IDS ids;

    @XmlElement(name = "Status")
    @JsonProperty("Status")
    private Status status;

    @XmlElement(name = "Description")
    @JsonProperty("Description")
    private Description description;

    @XmlElement(name = "Owner")
    @JsonProperty("Owner")
    private Owner owner;

    @XmlElement(name = "Providers")
    @JsonProperty("Providers")
    private Providers providers;

    @XmlElement(name = "Models")
    @JsonProperty("Models")
    private Models models;

    @XmlElement(name = "Attributes")
    @JsonProperty("Attributes")
    @JsonDeserialize(using = BioSampleDeserializers.AttributesDeserializer.class)
    private Attributes attributes;

    @XmlElement(name = "Links")
    @JsonProperty("Links")
    private Links links;

    @XmlElement(name = "Relations")
    @JsonProperty("Relations")
    private Relations relations;

    @XmlElement(name = "Package")
    @JsonProperty("Package")
    private PackageClass pkg; // 大事な情報.格納し、表示する。
}
