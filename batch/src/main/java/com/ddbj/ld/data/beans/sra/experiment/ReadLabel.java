package com.ddbj.ld.data.beans.sra.experiment;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = ExperimentDeserializers.ReadLabelDeserializer.class)
@Data
public class ReadLabel {
    @XmlAttribute(name = "read_group_tag")
    @JsonProperty("read_group_tag")
    private String readGroupTag;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
