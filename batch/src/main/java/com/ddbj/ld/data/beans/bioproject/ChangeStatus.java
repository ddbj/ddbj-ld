package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ChangeStatus {
    @XmlElement(name = "Target")
    @JsonProperty("Target")
    private Target target;

    @XmlElement(name = "Release")
    @JsonProperty("Release")
    private String release;

    @XmlElement(name = "SetReleaseDate")
    @JsonProperty("SetReleaseDate")
    private Hold setReleaseDate;

    @XmlElement(name = "Suppress")
    @JsonProperty("Suppress")
    private String suppress;

    @XmlElement(name = "AddComment")
    @JsonProperty("AddComment")
    private String addComment;
}
