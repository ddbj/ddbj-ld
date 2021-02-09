package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class RefSeq {
    private String representation;
    private Source annotationSource;
    private Source sequenceSource;
    private Source nomenclatureSource;

    @JsonProperty("representation")
    public String getRepresentation() { return representation; }
    @JsonProperty("representation")
    public void setRepresentation(String value) { this.representation = value; }

    @JsonProperty("AnnotationSource")
    public Source getAnnotationSource() { return annotationSource; }
    @JsonProperty("AnnotationSource")
    public void setAnnotationSource(Source value) { this.annotationSource = value; }

    @JsonProperty("SequenceSource")
    public Source getSequenceSource() { return sequenceSource; }
    @JsonProperty("SequenceSource")
    public void setSequenceSource(Source value) { this.sequenceSource = value; }

    @JsonProperty("NomenclatureSource")
    public Source getNomenclatureSource() { return nomenclatureSource; }
    @JsonProperty("NomenclatureSource")
    public void setNomenclatureSource(Source value) { this.nomenclatureSource = value; }
}
