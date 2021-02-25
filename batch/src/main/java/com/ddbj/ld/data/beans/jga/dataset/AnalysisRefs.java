package com.ddbj.ld.data.beans.jga.dataset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AnalysisRefs {
    private List<Ref> analysisRef;

    @JsonProperty("ANALYSIS_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = AnalysisRefs.AnalysisRefDeserializer.class)
    public List<Ref> getAnalysisRef() { return analysisRef; }
    @JsonProperty("ANALYSIS_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = AnalysisRefs.AnalysisRefDeserializer.class)
    public void setAnalysisRef(List<Ref> value) { this.analysisRef = value; }

    static class AnalysisRefDeserializer extends JsonDeserializer<List<Ref>> {
        @Override
        public List<Ref> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Ref> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Ref>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Ref.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize AnalysisRef");
            }
            return values;
        }
    }
}
