package com.ddbj.ld.data.beans.jga.policy;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PolicyLink {
    @XmlElement(name = "URL_LINK")
    @JsonProperty("URL_LINK")
    private URLLink urlLink;

    @XmlElement(name = "XREF_LINK")
    @JsonProperty("XREF_LINK")
    private Link xrefLink;

    @XmlElement(name = "ENTREZ_LINK")
    @JsonProperty("ENTREZ_LINK")
    private Link entrezLink;
}
