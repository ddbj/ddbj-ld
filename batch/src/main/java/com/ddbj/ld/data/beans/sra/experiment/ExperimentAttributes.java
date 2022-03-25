package com.ddbj.ld.data.beans.sra.experiment;

import com.ddbj.ld.data.beans.sra.common.Attribute;
import com.ddbj.ld.data.beans.sra.common.CommonDeserializers;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true) // xmlns:comをskip
@Data
public class ExperimentAttributes {
    @JsonProperty("EXPERIMENT_ATTRIBUTE")
    @JsonDeserialize(using = CommonDeserializers.AttributeListDeserializer.class)
    private List<Attribute> attribute;
}
