package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StudyTypes {
    @JsonProperty("STUDY_TYPE")
    @JsonDeserialize(using = StudyDeserializers.StudyTypeListDeserializer.class)
    private List<StudyType> studyType;
}
