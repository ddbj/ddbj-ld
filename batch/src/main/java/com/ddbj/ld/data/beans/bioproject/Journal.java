package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Journal {
    @XmlElement(name = "JournalTitle")
    @JsonProperty("JournalTitle")
    private String journalTitle;

    @XmlElement(name = "Year")
    @JsonProperty("Year")
    private String year;

    @XmlElement(name = "Volume")
    @JsonProperty("Volume")
    private String volume;

    @XmlElement(name = "Issue")
    @JsonProperty("Issue")
    private String issue;

    @XmlElement(name = "PagesFrom")
    @JsonProperty("PagesFrom")
    private String pagesFrom;

    @XmlElement(name = "PagesTo")
    @JsonProperty("PagesTo")
    private String pagesTo;
}
