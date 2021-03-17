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
public class AnalysisAttributes {
    private List<AnalysisAttribute> analysisAttribute;

    @JsonProperty("ANALYSIS_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = AnalysisAttributes.Deserializer.class)
    public List<AnalysisAttribute> getAnalysisAttribute() { return analysisAttribute; }
    @JsonProperty("ANALYSIS_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = AnalysisAttributes.Deserializer.class)
    public void setAnalysisAttribute(List<AnalysisAttribute> value) { this.analysisAttribute = value; }

    static class Deserializer extends JsonDeserializer<List<AnalysisAttribute>> {
        @Override
        public List<AnalysisAttribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<AnalysisAttribute> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<AnalysisAttribute>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, AnalysisAttribute.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize Deserializer");
            }
            return values;
        }
    }
}
