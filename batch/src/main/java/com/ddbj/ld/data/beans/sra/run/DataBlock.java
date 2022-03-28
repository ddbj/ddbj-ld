package com.ddbj.ld.data.beans.sra.run;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DataBlock {
    @XmlAttribute(name = "member_name")
    @JsonProperty("member_name")
    private String memberName;

    @XmlElement(name = "FILES")
    @JsonProperty("FILES")
    private Files files;
}
