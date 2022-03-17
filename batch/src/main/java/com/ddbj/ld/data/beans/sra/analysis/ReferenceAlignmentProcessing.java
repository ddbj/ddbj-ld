package com.ddbj.ld.data.beans.sra.analysis;

import com.ddbj.ld.data.beans.sra.common.AlignmentDirectives;
import com.ddbj.ld.data.beans.sra.common.Pipeline;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ReferenceAlignmentProcessing {
    @XmlElement(name = "PIPELINE")
    @JsonProperty("PIPELINE")
    private Pipeline pipeline;

    @XmlElement(name = "DIRECTIVES")
    @JsonProperty("DIRECTIVES")
    private AlignmentDirectives directives;
}
