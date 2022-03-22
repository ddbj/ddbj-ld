package com.ddbj.ld.data.beans.sra.submission;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Hold {
    @XmlAttribute(name = "target")
    @JsonProperty("target")
    private String target;

    @XmlAttribute(name = "HoldUntilDate")
    @JsonProperty("HoldUntilDate")
    private String holdUntilDate;
}
