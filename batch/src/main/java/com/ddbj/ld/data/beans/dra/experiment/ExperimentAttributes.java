package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExperimentAttributes {
    private List<ExperimentAttribute> experimentAttribute;

    @JsonProperty("EXPERIMENT_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExperimentAttributes.ExperimentAttributeDeserializer.class)
    public List<ExperimentAttribute> getExperimentAttribute() { return experimentAttribute; }
    @JsonProperty("EXPERIMENT_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExperimentAttributes.ExperimentAttributeDeserializer.class)
    public void setExperimentAttribute(List<ExperimentAttribute> value) { this.experimentAttribute = value; }

    static class ExperimentAttributeDeserializer extends JsonDeserializer<List<ExperimentAttribute>> {
        @Override
        public List<ExperimentAttribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<ExperimentAttribute> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<ExperimentAttribute>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, ExperimentAttribute.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize ExperimentAttributeDeserializer");
            }
            return values;
        }
    }
}
