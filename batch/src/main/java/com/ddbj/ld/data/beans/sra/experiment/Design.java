package com.ddbj.ld.data.beans.sra.experiment;

import com.ddbj.ld.data.beans.sra.common.SpotDescriptor;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true) // xmlns:comをskip
@Data
public class Design {
    @XmlElement(name = "DESIGN_DESCRIPTION")
    @JsonProperty("DESIGN_DESCRIPTION")
    private String designDescription;

    @XmlElement(name = "SAMPLE_DESCRIPTOR")
    @JsonProperty("SAMPLE_DESCRIPTOR")
    private StudyRef sampleDescriptor;

    @XmlElement(name = "LIBRARY_DESCRIPTOR")
    @JsonProperty("LIBRARY_DESCRIPTOR")
    private LibraryDescriptor libraryDescriptor;

    @XmlElement(name = "SPOT_DESCRIPTOR")
    @JsonProperty("SPOT_DESCRIPTOR")
    private SpotDescriptor spotDescriptor;
}
