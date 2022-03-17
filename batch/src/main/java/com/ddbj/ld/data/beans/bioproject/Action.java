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
public class Action {
    @XmlAttribute(name = "action_id")
    @JsonProperty("action_id")
    private String actionID;

    @XmlElement(name = "ProcessFile")
    @JsonProperty("ProcessFile")
    private File processFile;

    @XmlElement(name = "AddFiles")
    @JsonProperty("AddFiles")
    private AddFiles addFiles;

    @XmlElement(name = "AddData")
    @JsonProperty("AddData")
    private AddData addData;

    @XmlElement(name = "ChangeStatus")
    @JsonProperty("ChangeStatus")
    private ChangeStatus changeStatus;
}
