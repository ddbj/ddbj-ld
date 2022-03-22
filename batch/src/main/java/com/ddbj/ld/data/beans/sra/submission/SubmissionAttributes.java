package com.ddbj.ld.data.beans.sra.submission;

import com.ddbj.ld.data.beans.sra.common.Attribute;
import com.ddbj.ld.data.beans.sra.common.CommonDeserializers;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SubmissionAttributes {
    @XmlElement(name = "SUBMISSION_ATTRIBUTE")
    @JsonProperty("SUBMISSION_ATTRIBUTE")
    @JsonDeserialize(using = CommonDeserializers.AttributeListDeserializer.class)
    private List<Attribute> submissionAttribute;
}
