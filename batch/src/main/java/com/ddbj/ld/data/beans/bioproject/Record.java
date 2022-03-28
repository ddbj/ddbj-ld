package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Record {
    @XmlAttribute(name = "last_update")
    @JsonProperty("last_update")
    private String lastUpdate;

    @XmlAttribute(name = "status")
    @JsonProperty("status")
    private String status;

    @XmlAttribute(name = "error_message")
    @JsonProperty("error_message")
    private String errorMessage;

    @XmlElement(name = "Target")
    @JsonProperty("Target")
    private Target target;
}
