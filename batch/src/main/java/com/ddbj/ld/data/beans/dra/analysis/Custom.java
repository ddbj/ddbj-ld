package com.ddbj.ld.data.beans.dra.analysis;

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
public class Custom {
    private String description;
    private List<AnalysisLink> referenceSource;

    @JsonProperty("DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() { return description; }
    @JsonProperty("DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("REFERENCE_SOURCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Custom.Deserializer.class)
    public List<AnalysisLink> getReferenceSource() { return referenceSource; }
    @JsonProperty("REFERENCE_SOURCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Custom.Deserializer.class)
    public void setReferenceSource(List<AnalysisLink> value) { this.referenceSource = value; }

    static class Deserializer extends JsonDeserializer<List<AnalysisLink>> {
        @Override
        public List<AnalysisLink> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<AnalysisLink> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();
            mapper.coercionConfigFor(AnalysisLink.class).setAcceptBlankAsEmpty(true);

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<AnalysisLink>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, AnalysisLink.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize Custom.AnalysisLink");
            }
            return values;
        }
    }
}
