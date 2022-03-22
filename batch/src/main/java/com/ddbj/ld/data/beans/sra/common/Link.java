package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true) // ENTREZ_LINKを無視
@Data
public class Link {
    @XmlElement(name = "URL_LINK")
    @JsonProperty("URL_LINK")
    private URLLink urlLink;

    @XmlElement(name = "XREF_LINK")
    @JsonProperty("XREF_LINK")
    private XrefLink xrefLink;
}
