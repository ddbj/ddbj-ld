package com.ddbj.ld.data.beans.biosample;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;

@Slf4j
public class BioSampleClass implements IPropertiesBean {
    private Status status;
    private OffsetDateTime lastUpdate;
    private OffsetDateTime publicationDate;
    private String access;
    private Description description;
    private IDS ids;
    private Owner owner;
    private Providers providers;
    private Models models;
    private Attributes attributes;
    private Links links;
    private Relations relations;
    private String accession; // アクセッション番号. 重要なので格納
    private OffsetDateTime submissiondate; // 登録日. 重要なので格納
//    private String id; // NCBI Entrez 検索用の整数 ID. skip
    private Package pkg; // 大事な情報.格納し、表示する。

    @JsonProperty("Status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Status getStatus() { return status; }
    @JsonProperty("Status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStatus(Status value) { this.status = value; }

    @JsonProperty("last_update")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public OffsetDateTime getLastUpdate() { return lastUpdate; }
    @JsonProperty("last_update")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLastUpdate(OffsetDateTime value) { this.lastUpdate = value; }

    @JsonProperty("publication_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public OffsetDateTime getPublicationDate() { return publicationDate; }
    @JsonProperty("publication_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPublicationDate(OffsetDateTime value) { this.publicationDate = value; }

    @JsonProperty("access")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccess() { return access; }
    @JsonProperty("access")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccess(String value) { this.access = value; }

    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Description getDescription() { return description; }
    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(Description value) { this.description = value; }

    @JsonProperty("Ids")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public IDS getIDS() { return ids; }
    @JsonProperty("Ids")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIDS(IDS value) { this.ids = value; }

    @JsonProperty("Owner")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Owner getOwner() { return owner; }
    @JsonProperty("Owner")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOwner(Owner value) { this.owner = value; }

    @JsonProperty("Providers")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Providers getProviders() { return providers; }
    @JsonProperty("Providers")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProviders(Providers value) { this.providers = value; }

    @JsonProperty("Models")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Models getModels() { return models; }
    @JsonProperty("Models")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setModels(Models value) { this.models = value; }

    @JsonProperty("Attributes")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Attributes getAttributes() { return attributes; }
    @JsonProperty("Attributes")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAttributes(Attributes value) { this.attributes = value; }

    @JsonProperty("Links")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Links getLinks() { return links; }
    @JsonProperty("Links")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLinks(Links value) { this.links = value; }

    @JsonProperty("Relations")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Relations getRelations() { return relations; }
    @JsonProperty("Relations")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRelations(Relations value) { this.relations = value; }

    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccession() { return accession; }
    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccession(String value) { this.accession = value; }

    @JsonProperty("submission_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public OffsetDateTime getSubmissionDate() { return submissiondate; }
    @JsonProperty("submission_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSubmissionDate(OffsetDateTime value) { this.submissiondate = value; }

    @JsonProperty("Package")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Package getID() { return pkg; }
    @JsonProperty("Package")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(Package value) { this.pkg = value; }
}
