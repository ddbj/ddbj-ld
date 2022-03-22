package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = BioProjectDeserializers.LocusTagPrefixDeserializer.class)
@Data
public class LocusTagPrefix {
    @XmlAttribute(name = "biosample_id")
    @JsonProperty("biosample_id")
    private String bioSampleID;

    @XmlAttribute(name = "assembly_id")
    @JsonProperty("assembly_id")
    private String assemblyID;

    @XmlValue
    @JsonProperty("content")
    private String content;
}
