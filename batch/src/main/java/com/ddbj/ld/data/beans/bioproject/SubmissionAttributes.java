package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SubmissionAttributes {
    @XmlElement(name = "SUBMISSION_ATTRIBUTE")
    @JsonProperty("SUBMISSION_ATTRIBUTE")
    private SubmissionAttribute submissionAttribute;
}
