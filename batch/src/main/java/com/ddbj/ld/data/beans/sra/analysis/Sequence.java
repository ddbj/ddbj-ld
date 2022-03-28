package com.ddbj.ld.data.beans.sra.analysis;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Sequence {
    @XmlAttribute(name = "accession")
    @JsonProperty("accession")
    private String accession;

    @XmlAttribute(name = "gi")
    @JsonProperty("gi")
    private String gi;

    @XmlAttribute(name = "data_block_name")
    @JsonProperty("data_block_name")
    private String dataBlockName;

    @XmlAttribute(name = "seq_label")
    @JsonProperty("seq_label")
    private String seqLabel;
}
