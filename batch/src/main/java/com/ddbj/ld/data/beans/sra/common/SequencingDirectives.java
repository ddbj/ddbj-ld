package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SequencingDirectives {
    @XmlElement(name = "SAMPLE_DEMUX_DIRECTIVE")
    @JsonProperty("SAMPLE_DEMUX_DIRECTIVE")
    private String sampleDemuxDirective;
}
