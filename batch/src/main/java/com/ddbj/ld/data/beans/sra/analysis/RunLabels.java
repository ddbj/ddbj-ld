package com.ddbj.ld.data.beans.sra.analysis;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RunLabels {
    @XmlElement(name = "RUN")
    @JsonProperty("RUN")
    private Run run;
}
