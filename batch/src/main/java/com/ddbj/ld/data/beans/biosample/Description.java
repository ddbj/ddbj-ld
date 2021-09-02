package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;
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
@JsonIgnoreProperties(ignoreUnknown=true)
public class Description {
    private String sampleName;
//    private List<Synonym> synonym; // NCBI dbGaP の独自フィールド. スキップ.
    private String title;
    private List<Organism> organism;
    private Comment comment;

    @JsonProperty("SampleName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSampleName() { return sampleName; }
    @JsonProperty("SampleName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSampleName(String value) { this.sampleName = value; }

    @JsonProperty("Title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTitle() { return title; }
    @JsonProperty("Title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("Organism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Description.OrganismDeserializer.class)
    public List<Organism> getOrganism() { return organism; }
    @JsonProperty("Organism")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Description.OrganismDeserializer.class)
    public void setOrganism(List<Organism> value) { this.organism = value; }

    @JsonProperty("Comment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Comment getComment() { return comment; }
    @JsonProperty("Comment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setComment(Comment value) { this.comment = value; }

    static class OrganismDeserializer extends JsonDeserializer<List<Organism>> {
        @Override
        public List<Organism> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Organism>();
            var mapper = Converter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Organism>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Organism.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Description.OrganismDeserializer");
            }
            return values;
        }
    }
}
