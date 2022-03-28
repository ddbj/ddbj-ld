package com.ddbj.ld.data.beans.jga.dataset;

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
public class AnalysisRefs {
    @XmlElement(name = "ANALYSIS_REF")
    @JsonProperty("ANALYSIS_REF")
    @JsonDeserialize(using = DataSetDeserializers.RefListDeserializer.class)
    private List<Ref> analysisRef;
}
