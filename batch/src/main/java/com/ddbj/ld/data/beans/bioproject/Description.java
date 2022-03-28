package com.ddbj.ld.data.beans.bioproject;

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
public class Description {
    @XmlElement(name = "List")
    @JsonProperty("List")
    private String list;

    @XmlElement(name = "Submitter")
    @JsonProperty("Submitter")
    private Submitter submitter;

    @XmlElement(name = "Organization")
    @JsonProperty("Organization")
    @JsonDeserialize(using = BioProjectDeserializers.OrganizationListDeserializer.class)
    private List<Organization> organization;

    @XmlElement(name = "Hold")
    @JsonProperty("Hold")
    private Hold hold;

    @XmlElement(name = "Access")
    @JsonProperty("Access")
    private String access;
}
