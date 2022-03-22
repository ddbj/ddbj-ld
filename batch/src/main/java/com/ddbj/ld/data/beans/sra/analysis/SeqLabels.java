package com.ddbj.ld.data.beans.sra.analysis;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SeqLabels {
    @XmlElement(name = "SEQUENCE")
    @JsonProperty("SEQUENCE")
    @JsonDeserialize(using = AnalysisDeserializers.SequenceListDeserializer.class)
    private List<Sequence> sequence;
}
