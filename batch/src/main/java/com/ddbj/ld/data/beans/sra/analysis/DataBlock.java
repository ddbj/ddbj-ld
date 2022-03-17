package com.ddbj.ld.data.beans.sra.analysis;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DataBlock {
    @XmlAttribute(name = "name")
    @JsonProperty("name")
    private String name;

    @XmlAttribute(name = "serial")
    @JsonProperty("serial")
    private String serial;

    @XmlAttribute(name = "member")
    @JsonProperty("member")
    private String member;

    @XmlElement(name = "FILES")
    @JsonProperty("FILES")
    @JsonDeserialize(using = AnalysisDeserializers.FilesDeserializer.class)
    private Files files;
}
