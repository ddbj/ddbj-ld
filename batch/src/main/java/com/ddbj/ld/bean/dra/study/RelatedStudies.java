package com.ddbj.ld.bean.dra.study;

import com.fasterxml.jackson.annotation.*;

public class RelatedStudies {
    private RelatedStudy relatedStudy;

    @JsonProperty("RELATED_STUDY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RelatedStudy getRelatedStudy() { return relatedStudy; }
    @JsonProperty("RELATED_STUDY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRelatedStudy(RelatedStudy value) { this.relatedStudy = value; }
}
