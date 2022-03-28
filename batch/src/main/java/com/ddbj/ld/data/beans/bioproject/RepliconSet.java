package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RepliconSet {
    @XmlElement(name = "Replicon")
    @JsonProperty("Replicon")
    @JsonDeserialize(using = BioProjectDeserializers.RepliconListDeserializer.class)
    private List<Replicon> replicon;

    @XmlElement(name = "Ploidy")
    @JsonProperty("Ploidy")
    private Ploidy ploidy;

    @XmlElement(name = "Count")
    @JsonProperty("Count")
    @JsonDeserialize(using = BioProjectDeserializers.CountListDeserializer.class)
    private List<Count> count;
}
