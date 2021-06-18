package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@JsonIgnoreProperties(ignoreUnknown=true)
public class Organism {
    private String taxonomyID;
//    private String taxonomyName; //  OrganismName と同一と思われる. スキップ
    private String role;
    private String organismName;
    private String label;
    private String isolateName;
    private String breed;
    private String strain;
    private String cultivar;

    @JsonProperty("taxonomy_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Organism.TaxonomyIDDeserializer.class)
    public String getTaxonomyID() { return taxonomyID; }
    @JsonProperty("taxonomy_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Organism.TaxonomyIDDeserializer.class)
    public void setTaxonomyID(String value) { this.taxonomyID = value; }

    @JsonProperty("role")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRole() { return role; }
    @JsonProperty("role")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRole(String value) { this.role = value; }

    @JsonProperty("OrganismName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOrganismName() { return organismName; }
    @JsonProperty("OrganismName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOrganismName(String value) { this.organismName = value; }

    @JsonProperty("Label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLabel() { return label; }
    @JsonProperty("Label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(String value) { this.label = value; }

    @JsonProperty("IsolateName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIsolateName() { return isolateName; }
    @JsonProperty("IsolateName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIsolateName(String value) { this.isolateName = value; }

    @JsonProperty("Breed")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBreed() { return breed; }
    @JsonProperty("Breed")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBreed(String value) { this.breed = value; }

    @JsonProperty("Strain")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStrain() { return strain; }
    @JsonProperty("Strain")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStrain(String value) { this.strain = value; }

    @JsonProperty("Cultivar")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCultivar() { return cultivar; }
    @JsonProperty("Cultivar")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCultivar(String value) { this.cultivar = value; }

    static class TaxonomyIDDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = new String();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = jsonParser.readValueAs(Integer.class).toString();
                    break;
                case VALUE_STRING:
                    value = jsonParser.readValueAs(String.class);
                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Organism.TaxonomyIDDeserializer");
            }
            return value;
        }
    }
}
