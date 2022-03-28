package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SubmissionLink {
    @XmlElement(name = "URL_LINK")
    @JsonProperty("URL_LINK")
    private URLLink urlLink;

    @XmlElement(name = "XREF_LINK")
    @JsonProperty("XREF_LINK")
    private XrefLink xrefLink;
}
