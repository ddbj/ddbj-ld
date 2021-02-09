package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;

public class ProjectDescr {
    private String name;
    private String title;
    private String description;
    private ExternalLink externalLink;
    private Grant grant;
    private Publication publication;
    private OffsetDateTime projectReleaseDate;
    private String keyword;
    private Relevance relevance;
    private String locusTagPrefix;
    private UserTerm userTerm;
    private RefSeq refSeq;

    @JsonProperty("Name")
    public String getName() { return name; }
    @JsonProperty("Name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("Title")
    public String getTitle() { return title; }
    @JsonProperty("Title")
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("Description")
    public String getDescription() { return description; }
    @JsonProperty("Description")
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("ExternalLink")
    public ExternalLink getExternalLink() { return externalLink; }
    @JsonProperty("ExternalLink")
    public void setExternalLink(ExternalLink value) { this.externalLink = value; }

    @JsonProperty("Grant")
    public Grant getGrant() { return grant; }
    @JsonProperty("Grant")
    public void setGrant(Grant value) { this.grant = value; }

    @JsonProperty("Publication")
    public Publication getPublication() { return publication; }
    @JsonProperty("Publication")
    public void setPublication(Publication value) { this.publication = value; }

    @JsonProperty("ProjectReleaseDate")
    public OffsetDateTime getProjectReleaseDate() { return projectReleaseDate; }
    @JsonProperty("ProjectReleaseDate")
    public void setProjectReleaseDate(OffsetDateTime value) { this.projectReleaseDate = value; }

    @JsonProperty("Keyword")
    public String getKeyword() { return keyword; }
    @JsonProperty("Keyword")
    public void setKeyword(String value) { this.keyword = value; }

    @JsonProperty("Relevance")
    public Relevance getRelevance() { return relevance; }
    @JsonProperty("Relevance")
    public void setRelevance(Relevance value) { this.relevance = value; }

    @JsonProperty("LocusTagPrefix")
    public String getLocusTagPrefix() { return locusTagPrefix; }
    @JsonProperty("LocusTagPrefix")
    public void setLocusTagPrefix(String value) { this.locusTagPrefix = value; }

    @JsonProperty("UserTerm")
    public UserTerm getUserTerm() { return userTerm; }
    @JsonProperty("UserTerm")
    public void setUserTerm(UserTerm value) { this.userTerm = value; }

    @JsonProperty("RefSeq")
    public RefSeq getRefSeq() { return refSeq; }
    @JsonProperty("RefSeq")
    public void setRefSeq(RefSeq value) { this.refSeq = value; }
}
