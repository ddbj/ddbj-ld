package com.ddbj.ld.data.beans.sra.analysis;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ReferenceAlignment {
    @XmlElement(name = "ASSEMBLY")
    @JsonProperty("ASSEMBLY")
    private Assembly assembly;

    @XmlElement(name = "RUN_LABELS")
    @JsonProperty("RUN_LABELS")
    private RunLabels runLabels;

    @XmlElement(name = "SEQ_LABELS")
    @JsonProperty("SEQ_LABELS")
    private SeqLabels seqLabels;

    @XmlElement(name = "PROCESSING")
    @JsonProperty("PROCESSING")
    private ReferenceAlignmentProcessing processing;
}
