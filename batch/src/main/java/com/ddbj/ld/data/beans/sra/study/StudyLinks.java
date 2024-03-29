package com.ddbj.ld.data.beans.sra.study;

import com.ddbj.ld.data.beans.sra.common.CommonDeserializers;
import com.ddbj.ld.data.beans.sra.common.Link;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown=true) // xmlns:comを無視する
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StudyLinks {
    @XmlElement(name = "STUDY_LINK")
    @JsonProperty("STUDY_LINK")
    @JsonDeserialize(using = CommonDeserializers.LinkListDeserializer.class)
    private List<Link> studyLink;
}
