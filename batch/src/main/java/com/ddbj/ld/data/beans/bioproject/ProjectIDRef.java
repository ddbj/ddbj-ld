package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProjectIDRef {
    @XmlAttribute(name = "archive")
    @JsonProperty("archive")
    private String archive;

    @XmlAttribute(name = "id")
    @JsonProperty("id")
    private String id;

    @XmlAttribute(name = "accession")
    @JsonProperty("accession")
    private String accession;
}
