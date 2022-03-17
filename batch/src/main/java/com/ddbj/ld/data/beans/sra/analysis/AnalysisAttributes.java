package com.ddbj.ld.data.beans.sra.analysis;

import com.ddbj.ld.data.beans.sra.common.Attribute;
import com.ddbj.ld.data.beans.sra.common.CommonDeserializers;
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
public class AnalysisAttributes {
    @XmlElement(name = "ANALYSIS_ATTRIBUTE")
    @JsonProperty("ANALYSIS_ATTRIBUTE")
    @JsonDeserialize(using = CommonDeserializers.AttributeListDeserializer.class)
    private List<Attribute> analysisAttribute;
}
