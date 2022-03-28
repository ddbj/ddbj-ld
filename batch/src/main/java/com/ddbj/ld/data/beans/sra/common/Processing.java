package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Processing {
    @XmlElement(name = "PIPELINE")
    @JsonProperty("PIPELINE")
    private Pipeline pipeline;

    @XmlElement(name = "DIRECTIVES")
    @JsonProperty("DIRECTIVES")
    @JsonDeserialize(using = CommonDeserializers.SequencingDirectivesDeserializer.class)
    private SequencingDirectives directives;
}
