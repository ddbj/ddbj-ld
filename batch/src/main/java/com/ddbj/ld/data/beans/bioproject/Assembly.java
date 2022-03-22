package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Assembly {
    @XmlAttribute(name = "assemblyName")
    @JsonProperty("assemblyName")
    private String assemblyName;

    @XmlAttribute(name = "assemblyAccession")
    @JsonProperty("assemblyAccession")
    private String assemblyAccession;

    @XmlAttribute(name = "WGSprefix")
    @JsonProperty("WGSprefix")
    private String wgSprefix;

    @XmlAttribute(name = "LocusTagPrefix")
    @JsonProperty("LocusTagPrefix")
    private String locusTagPrefix;

    @XmlElement(name = "Replicon")
    @JsonProperty("Replicon")
    @JsonDeserialize(using = BioProjectDeserializers.RepliconListDeserializer.class)
    private List<Replicon> replicon;
}
