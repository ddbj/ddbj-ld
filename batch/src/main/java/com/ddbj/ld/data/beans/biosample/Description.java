package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true) // NCBI dbGaP の独自フィールドsynonymをスキップ
@Data
public class Description {
    @XmlElement(name = "SampleName")
    @JsonProperty("SampleName")
    private String sampleName;

    @XmlElement(name = "Title")
    @JsonProperty("Title")
    private String title;

    @XmlElement(name = "Organism")
    @JsonProperty("Organism")
    @JsonDeserialize(using = BioSampleDeserializers.OrganismListDeserializer.class)
    private List<BioSampleOrganism> organism;

    @XmlElement(name = "Comment")
    @JsonProperty("Comment")
    private Comment comment;
}
