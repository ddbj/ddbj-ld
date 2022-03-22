package com.ddbj.ld.data.beans.bioproject;

import com.ddbj.ld.data.beans.common.Deserializers;
import com.ddbj.ld.data.beans.common.OffsetDateTimeAdapter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.OffsetDateTime;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProjectDescr {
    @XmlElement(name = "Name")
    @JsonProperty("Name")
    private String name;

    @XmlElement(name = "Title")
    @JsonProperty("Title")
    private String title;

    @XmlElement(name = "Description")
    @JsonProperty("Description")
    private String description;

    @XmlElement(name = "ExternalLink")
    @JsonProperty("ExternalLink")
    @JsonDeserialize(using = BioProjectDeserializers.ExternalLinkListDeserializer.class)
    private List<ExternalLink> externalLink;

    @XmlElement(name = "Grant")
    @JsonProperty("Grant")
    @JsonDeserialize(using = BioProjectDeserializers.GrantListDeserializer.class)
    private List<Grant> grant;

    @XmlElement(name = "Publication")
    @JsonProperty("Publication")
    @JsonDeserialize(using = BioProjectDeserializers.PublicationListDeserializer.class)
    private List<Publication> publication;

    @XmlElement(name = "ProjectReleaseDate")
    @XmlJavaTypeAdapter(OffsetDateTimeAdapter.class)
    @JsonProperty("ProjectReleaseDate")
    private OffsetDateTime projectReleaseDate;

    @XmlElement(name = "Keyword")
    @JsonProperty("Keyword")
    @JsonDeserialize(using = Deserializers.StringListDeserializer.class)
    private List<String> keyword;

    @XmlElement(name = "Relevance")
    @JsonProperty("Relevance")
    private Relevance relevance;

    @XmlElement(name = "LocusTagPrefix")
    @JsonProperty("LocusTagPrefix")
    @JsonDeserialize(using = BioProjectDeserializers.LocusTagPrefixListDeserializer.class)
    private List<LocusTagPrefix> locusTagPrefix;

    @XmlElement(name = "UserTerm")
    @JsonProperty("UserTerm")
    @JsonDeserialize(using = BioProjectDeserializers.UserTermListDeserializers.class)
    private List<UserTerm> userTerm;

    @XmlElement(name = "RefSeq")
    @JsonProperty("RefSeq")
    private RefSeq refSeq;
}
