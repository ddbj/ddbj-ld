package com.ddbj.ld.data.beans.sra.experiment;

import com.ddbj.ld.data.beans.sra.common.Identifiers;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Member {
    @XmlAttribute(name = "refname")
    @JsonProperty("refname")
    private String refname;

    @XmlAttribute(name = "refcenter")
    @JsonProperty("refcenter")
    private String refcenter;

    @XmlAttribute(name = "accession")
    @JsonProperty("accession")
    private String accession;

    @XmlAttribute(name = "member_name")
    @JsonProperty("member_name")
    private String memberName;

    @XmlAttribute(name = "proportion")
    @JsonProperty("proportion")
    private String proportion;

    @XmlElement(name = "IDENTIFIERS")
    @JsonProperty("IDENTIFIERS")
    private Identifiers identifiers;

    @XmlElement(name = "READ_LABEL")
    @JsonProperty("READ_LABEL")
    @JsonDeserialize(using = ExperimentDeserializers.ReadLabelListDeserializer.class)
    private List<ReadLabel> readLabel;
}
