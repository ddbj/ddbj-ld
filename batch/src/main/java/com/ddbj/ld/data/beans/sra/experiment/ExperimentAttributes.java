package com.ddbj.ld.data.beans.sra.experiment;

import com.ddbj.ld.data.beans.sra.common.Attribute;
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
@JsonIgnoreProperties(ignoreUnknown=true) // xmlns:comを無視
public class ExperimentAttributes {
    private List<Attribute> attribute;

    @JsonProperty("EXPERIMENT_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExperimentAttributes.ExperimentAttributeDeserializer.class)
    public List<Attribute> getExperimentAttribute() { return attribute; }
    @JsonProperty("EXPERIMENT_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExperimentAttributes.ExperimentAttributeDeserializer.class)
    public void setExperimentAttribute(List<Attribute> value) { this.attribute = value; }

    static class ExperimentAttributeDeserializer extends JsonDeserializer<List<Attribute>> {
        @Override
        public List<Attribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Attribute>();
            var mapper = ExperimentConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Attribute>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Attribute.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ExperimentAttributes.ExperimentAttributeDeserializer");
            }
            return values;
        }
    }
}
