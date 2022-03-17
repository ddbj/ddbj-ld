package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Journal {
    @XmlElement(name = "JOURNAL_TITLE")
    @JsonProperty("JOURNAL_TITLE")
    private String journalTitle;

    @XmlElement(name = "YEAR")
    @JsonProperty("YEAR")
    private String year;

    @XmlElement(name = "VOLUME")
    @JsonProperty("VOLUME")
    private String volume;

    @XmlElement(name = "ISSUE")
    @JsonProperty("ISSUE")
    private String issue;

    @XmlElement(name = "PAGES_FROM")
    @JsonProperty("PAGES_FROM")
    private String pagesFrom;

    @XmlElement(name = "PAGES_TO")
    @JsonProperty("PAGES_TO")
    private String pagesTo;
}
