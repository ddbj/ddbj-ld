package com.ddbj.ld.data.beans.sra.run;

import com.ddbj.ld.data.beans.sra.common.CommonDeserializers;
import com.ddbj.ld.data.beans.sra.common.Identifiers;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ExperimentRef {
    @XmlAttribute(name = "refname")
    @JsonProperty("refname")
    private String refname;

    @XmlAttribute(name = "refcenter")
    @JsonProperty("refcenter")
    private String refcenter;

    @XmlAttribute(name = "accession")
    @JsonProperty("accession")
    private String accession;

    @XmlElement(name = "IDENTIFIERS")
    @JsonProperty("IDENTIFIERS")
    @JsonDeserialize(using = CommonDeserializers.IdentifiersDeserializer.class)
    private Identifiers identifiers;
}
