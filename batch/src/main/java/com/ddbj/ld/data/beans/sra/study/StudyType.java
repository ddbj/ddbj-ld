package com.ddbj.ld.data.beans.sra.study;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StudyType {
    @XmlAttribute(name = "existing_study_type")
    @JsonProperty("existing_study_type")
    private String existingStudyType;

    @XmlAttribute(name = "new_study_type")
    @JsonProperty("new_study_type")
    private String newStudyType;
}
