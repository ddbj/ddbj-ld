package com.ddbj.ld.data.beans.jga.dataset;

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
public class DataRefs {
    @XmlElement(name = "DATA_REF")
    @JsonProperty("DATA_REF")
    @JsonDeserialize(using = DataSetDeserializers.DataRefListDeserializer.class)
    private List<Ref> dataRef;
}
