package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Description {
    private String list;
    private Submitter submitter;
    private List<Organization> organization;
    private Hold hold;
    private String access;

    @JsonProperty("List")
    public String getList() { return list; }
    @JsonProperty("List")
    public void setList(String value) { this.list = value; }

    @JsonProperty("Submitter")
    public Submitter getSubmitter() { return submitter; }
    @JsonProperty("Submitter")
    public void setSubmitter(Submitter value) { this.submitter = value; }

    @JsonProperty("Organization")
    @JsonDeserialize(using = Description.OrganizationDeserializer.class)
    public List<Organization> getOrganization() { return organization; }
    @JsonProperty("Organization")
    @JsonDeserialize(using = Description.OrganizationDeserializer.class)
    public void setOrganization(List<Organization> value) { this.organization = value; }

    @JsonProperty("Hold")
    public Hold getHold() { return hold; }
    @JsonProperty("Hold")
    public void setHold(Hold value) { this.hold = value; }

    @JsonProperty("Access")
    public String getAccess() { return access; }
    @JsonProperty("Access")
    public void setAccess(String value) { this.access = value; }

    static class OrganizationDeserializer extends JsonDeserializer<List<Organization>> {
        @Override
        public List<Organization> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Organization>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<Organization>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = Converter.getObjectMapper().readValue(jsonParser, Organization.class);

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
