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
public class Link {
    @XmlElement(name = "ProjectIDRef")
    @JsonProperty("ProjectIDRef")
    private ProjectIDRef projectIDRef;

    @XmlElement(name = "Hierarchical")
    @JsonProperty("Hierarchical")
    private Hierarchical hierarchical;

    @XmlElement(name = "PeerProject")
    @JsonProperty("PeerProject")
    private PeerProject peerProject;

    @XmlElement(name = "ID")
    @JsonProperty("ID")
    private ID id;

    @XmlElement(name = "LinkExplanation")
    @JsonProperty("LinkExplanation")
    private String linkExplanation;
}
