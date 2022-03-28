package com.ddbj.ld.data.beans.sra.experiment;

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
public class TargetedLoci {
    @XmlElement(name = "LOCUS")
    @JsonProperty("LOCUS")
    @JsonDeserialize(using = ExperimentDeserializers.LocusListDeserializer.class)
    private List<Locus> locus;
}
