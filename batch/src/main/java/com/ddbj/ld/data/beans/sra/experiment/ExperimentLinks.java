package com.ddbj.ld.data.beans.sra.experiment;

import com.ddbj.ld.data.beans.sra.common.CommonDeserializers;
import com.ddbj.ld.data.beans.sra.common.Link;
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
public class ExperimentLinks {
    @XmlElement(name = "EXPERIMENT_LINK")
    @JsonProperty("EXPERIMENT_LINK")
    @JsonDeserialize(using = CommonDeserializers.LinkListDeserializer.class)
    private List<Link> link;
}
