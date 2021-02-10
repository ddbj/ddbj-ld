package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;

public class ProjectDescr {
    private String name;
    private String title;
    private String description;
    private List<ExternalLink> externalLink;
    private List<Grant> grant;
    private List<Publication> publication;
    private OffsetDateTime projectReleaseDate;
    private List<String> keyword;
    private List<Relevance> relevance;
    // FIXME 要整理、DDBJにもNCBIにも属性の情報は書いていなかったが、属性が記載されていたため直した
    private List<LocusTagPrefix> locusTagPrefix;
    private List<UserTerm> userTerm;
    private RefSeq refSeq;

    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() { return name; }
    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(String value) { this.name = value; }

    @JsonProperty("Title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTitle() { return title; }
    @JsonProperty("Title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() { return description; }
    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("ExternalLink")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ExternalLink> getExternalLink() { return externalLink; }
    @JsonProperty("ExternalLink")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExternalLink(List<ExternalLink> value) { this.externalLink = value; }

    @JsonProperty("Grant")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Grant> getGrant() { return grant; }
    @JsonProperty("Grant")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGrant(List<Grant> value) { this.grant = value; }

    @JsonProperty("Publication")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Publication> getPublication() { return publication; }
    @JsonProperty("Publication")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPublication(List<Publication> value) { this.publication = value; }

    @JsonProperty("ProjectReleaseDate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public OffsetDateTime getProjectReleaseDate() { return projectReleaseDate; }
    @JsonProperty("ProjectReleaseDate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectReleaseDate(OffsetDateTime value) { this.projectReleaseDate = value; }

    @JsonProperty("Keyword")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getKeyword() { return keyword; }
    @JsonProperty("Keyword")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setKeyword(List<String> value) { this.keyword = value; }

    @JsonProperty("Relevance")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Relevance> getRelevance() { return relevance; }
    @JsonProperty("Relevance")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRelevance(List<Relevance> value) { this.relevance = value; }

    @JsonProperty("LocusTagPrefix")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<LocusTagPrefix> getLocusTagPrefix() { return locusTagPrefix; }
    @JsonProperty("LocusTagPrefix")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocusTagPrefix(List<LocusTagPrefix> value) { this.locusTagPrefix = value; }

    @JsonProperty("UserTerm")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<UserTerm> getUserTerm() { return userTerm; }
    @JsonProperty("UserTerm")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUserTerm(List<UserTerm> value) { this.userTerm = value; }

    @JsonProperty("RefSeq")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RefSeq getRefSeq() { return refSeq; }
    @JsonProperty("RefSeq")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRefSeq(RefSeq value) { this.refSeq = value; }
}
