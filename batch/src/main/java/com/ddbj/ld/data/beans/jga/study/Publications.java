package com.ddbj.ld.data.beans.jga.study;

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
public class Publications {
    @XmlElement(name = "PUBLICATION")
    @JsonProperty("PUBLICATION")
    @JsonDeserialize(using = StudyDeserializers.PublicationListDeserializer.class)
    private List<Publication> publication;
}
