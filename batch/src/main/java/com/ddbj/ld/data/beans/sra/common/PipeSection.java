package com.ddbj.ld.data.beans.sra.common;

import com.ddbj.ld.data.beans.common.Deserializers;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PipeSection {
    @XmlAttribute(name = "section_name")
    @JsonProperty("section_name")
    private String sectionName;

    @XmlElement(name = "STEP_INDEX")
    @JsonProperty("STEP_INDEX")
    private String stepIndex;

    @XmlElement(name = "PREV_STEP_INDEX")
    @JsonProperty("PREV_STEP_INDEX")
    @JsonDeserialize(using = Deserializers.StringListDeserializer.class)
    private List<String> prevStepIndex;

    @XmlElement(name = "PROGRAM")
    @JsonProperty("PROGRAM")
    private String program;

    @XmlElement(name = "VERSION")
    @JsonProperty("VERSION")
    private String version;

    @XmlElement(name = "NOTES")
    @JsonProperty("NOTES")
    private String notes;
}
