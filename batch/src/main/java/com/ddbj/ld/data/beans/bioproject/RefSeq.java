package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class RefSeq {
    private String representation;
    private Source annotationSource;
    private Source sequenceSource;
    private Source nomenclatureSource;

    @JsonProperty("representation")
<<<<<<< HEAD
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
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRepresentation() { return representation; }
    @JsonProperty("representation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRepresentation(String value) { this.representation = value; }

    @JsonProperty("AnnotationSource")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Source getAnnotationSource() { return annotationSource; }
    @JsonProperty("AnnotationSource")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnnotationSource(Source value) { this.annotationSource = value; }

    @JsonProperty("SequenceSource")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Source getSequenceSource() { return sequenceSource; }
    @JsonProperty("SequenceSource")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSequenceSource(Source value) { this.sequenceSource = value; }

    @JsonProperty("NomenclatureSource")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Source getNomenclatureSource() { return nomenclatureSource; }
    @JsonProperty("NomenclatureSource")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setNomenclatureSource(Source value) { this.nomenclatureSource = value; }
}
