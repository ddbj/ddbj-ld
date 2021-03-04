package com.ddbj.ld.data.beans.dra.analysis;

<<<<<<< HEAD
import com.ddbj.ld.data.beans.dra.common.Attribute;
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
    private List<Attribute> analysisAttribute;

    @JsonProperty("ANALYSIS_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = AnalysisAttributes.AttributeDeserializer.class)
    public List<Attribute> getAnalysisAttribute() { return analysisAttribute; }
    @JsonProperty("ANALYSIS_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = AnalysisAttributes.AttributeDeserializer.class)
    public void setAnalysisAttribute(List<Attribute> value) { this.analysisAttribute = value; }

    static class AttributeDeserializer extends JsonDeserializer<List<Attribute>> {
        @Override
        public List<Attribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Attribute> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
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
                    log.error("Cannot deserialize AnalysisAttributes.AttributeDeserializer");
            }
            return values;
        }
    }
=======
import com.fasterxml.jackson.annotation.*;

public class AnalysisAttributes {
    private AnalysisAttribute analysisAttribute;

    @JsonProperty("ANALYSIS_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AnalysisAttribute getAnalysisAttribute() { return analysisAttribute; }
    @JsonProperty("ANALYSIS_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAnalysisAttribute(AnalysisAttribute value) { this.analysisAttribute = value; }
>>>>>>> 取り込み、修正
}
