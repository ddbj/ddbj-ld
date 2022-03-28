package com.ddbj.ld.data.beans.biosample;

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
public class Owner {
    @XmlElement(name = "Name")
    @JsonProperty("Name")
    @JsonDeserialize(using = BioSampleDeserializers.NameElementListDeserializer.class)
    private List<NameElement> name;

    @XmlElement(name = "Contacts")
    @JsonProperty("Contacts")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Contacts contacts;
}
