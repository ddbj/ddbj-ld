package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RelativeOrder {
    @XmlAttribute(name = "follows_read_index")
    @JsonProperty("follows_read_index")
    private String followsReadIndex;

    @XmlAttribute(name = "precedes_read_index")
    @JsonProperty("precedes_read_index")
    private String precedesReadIndex;
}
