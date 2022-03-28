package com.ddbj.ld.data.beans.sra.study;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RelatedStudies {
    @XmlElement(name = "RELATED_STUDY")
    @JsonProperty("RELATED_STUDY")
    @JsonDeserialize(using = StudyDeserializers.RelatedStudyListDeserializer.class)
    private List<RelatedStudy> relatedStudy;
}
