package com.ddbj.ld.data.beans.biosample;

import com.ddbj.ld.data.beans.common.Deserializers;
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
public class Comment {
    @XmlElement(name = "Paragraph")
    @JsonProperty("Paragraph")
    @JsonDeserialize(using = Deserializers.StringListDeserializer.class)
    private List<String> paragraph;

    @XmlElement(name = "Table")
    @JsonProperty("Table")
    @JsonDeserialize(using = BioSampleDeserializers.TableListDeserializer.class)
    private List<Table> table;
}
