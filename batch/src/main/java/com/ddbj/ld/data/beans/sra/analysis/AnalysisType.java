package com.ddbj.ld.data.beans.sra.analysis;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AnalysisType {
    @XmlElement(name = "DE_NOVO_ASSEMBLY")
    @JsonProperty("DE_NOVO_ASSEMBLY")
    private AbundanceMeasurement deNovoAssembly;

    @XmlElement(name = "REFERENCE_ALIGNMENT")
    @JsonProperty("REFERENCE_ALIGNMENT")
    private ReferenceAlignment referenceAlignment;

    @XmlElement(name = "SEQUENCE_ANNOTATION")
    @JsonProperty("SEQUENCE_ANNOTATION")
    private AbundanceMeasurement sequenceAnnotation;

    @XmlElement(name = "ABUNDANCE_MEASUREMENT")
    @JsonProperty("ABUNDANCE_MEASUREMENT")
    private AbundanceMeasurement abundanceMeasurement;
}
