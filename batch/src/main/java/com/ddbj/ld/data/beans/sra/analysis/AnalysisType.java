package com.ddbj.ld.data.beans.sra.analysis;

import com.fasterxml.jackson.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnalysisType {
    private AbundanceMeasurement deNovoAssembly;
    private ReferenceAlignment referenceAlignment;
    private AbundanceMeasurement sequenceAnnotation;
    private AbundanceMeasurement abundanceMeasurement;

    @JsonProperty("DE_NOVO_ASSEMBLY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AbundanceMeasurement getDeNovoAssembly() { return deNovoAssembly; }
    @JsonProperty("DE_NOVO_ASSEMBLY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDeNovoAssembly(AbundanceMeasurement value) { this.deNovoAssembly = value; }

    @JsonProperty("REFERENCE_ALIGNMENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ReferenceAlignment getReferenceAlignment() { return referenceAlignment; }
    @JsonProperty("REFERENCE_ALIGNMENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReferenceAlignment(ReferenceAlignment value) { this.referenceAlignment = value; }

    @JsonProperty("SEQUENCE_ANNOTATION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AbundanceMeasurement getSequenceAnnotation() { return sequenceAnnotation; }
    @JsonProperty("SEQUENCE_ANNOTATION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSequenceAnnotation(AbundanceMeasurement value) { this.sequenceAnnotation = value; }

    @JsonProperty("ABUNDANCE_MEASUREMENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AbundanceMeasurement getAbundanceMeasurement() { return abundanceMeasurement; }
    @JsonProperty("ABUNDANCE_MEASUREMENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAbundanceMeasurement(AbundanceMeasurement value) { this.abundanceMeasurement = value; }
}
