package com.ddbj.ld.data.beans.sra.analysis;

import com.ddbj.ld.data.beans.sra.common.XrefLink;
import com.fasterxml.jackson.annotation.*;
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
public class Standard {
    @XmlAttribute(name = "short_name")
    @JsonProperty("short_name")
    private String shortName;

    @XmlElement(name = "NAME")
    @JsonProperty("NAME")
    @JsonDeserialize(using = AnalysisDeserializers.XrefLinkListDeserializer.class)
    private List<XrefLink> name;
}
