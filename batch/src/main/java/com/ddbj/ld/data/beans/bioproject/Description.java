package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
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

@Slf4j
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Description {
    @XmlElement(name="List")
    @JsonProperty("List")
    private String list;

    @XmlElement(name="Submitter")
    @JsonProperty("Submitter")
    private Submitter submitter;

    @XmlElement(name="Organization")
    @JsonProperty("Organization")
    @JsonDeserialize(using = Description.OrganizationDeserializer.class)
    private List<Organization> organization;

    @XmlElement(name="Hold")
    @JsonProperty("Hold")
    private Hold hold;

    @XmlElement(name="Access")
    @JsonProperty("Access")
    private String access;

    static class OrganizationDeserializer extends JsonDeserializer<List<Organization>> {
        @Override
        public List<Organization> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Organization>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<Organization>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = BioProjectConverter.getObjectMapper().readValue(jsonParser, Organization.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Organization");
            }
            return values;
        }
    }
}
