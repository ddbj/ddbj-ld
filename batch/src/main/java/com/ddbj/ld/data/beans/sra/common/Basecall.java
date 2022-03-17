package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = CommonDeserializers.BasecallDeserializer.class)
@Data
public class Basecall {
    @XmlAttribute(name = "read_group_tag")
    @JsonProperty("read_group_tag")
    private String readGroupTag;

    @XmlAttribute(name = "min_match")
    @JsonProperty("min_match")
    private String minMatch;

    @XmlAttribute(name = "max_mismatch")
    @JsonProperty("max_mismatch")
    private String maxMismatch;

    @XmlAttribute(name = "match_edge")
    @JsonProperty("match_edge")
    private String matchEdge;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
