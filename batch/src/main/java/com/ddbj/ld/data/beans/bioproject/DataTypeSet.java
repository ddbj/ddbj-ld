package com.ddbj.ld.data.beans.bioproject;

import com.ddbj.ld.data.beans.common.Deserializers;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class DataTypeSet {
    @XmlElement(name = "DataType")
    @JsonProperty("DataType")
    @JsonDeserialize(using = Deserializers.StringListDeserializer.class)
    private List<String> dataType;
}
