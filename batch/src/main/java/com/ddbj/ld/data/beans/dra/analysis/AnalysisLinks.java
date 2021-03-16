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
public class AnalysisLinks {
    private List<AnalysisLink> analysisLink;

    @JsonProperty("ANALYSIS_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = AnalysisLinks.AnalysisLinkDeserializer.class)
    public List<AnalysisLink> getAnalysisLink() { return analysisLink; }
    @JsonProperty("ANALYSIS_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = AnalysisLinks.AnalysisLinkDeserializer.class)
    public void setAnalysisLink(List<AnalysisLink> value) { this.analysisLink = value; }

    static class AnalysisLinkDeserializer extends JsonDeserializer<List<AnalysisLink>> {
        @Override
        public List<AnalysisLink> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<AnalysisLink> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
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
                    log.error("Cannot deserialize AnalysisLinkDeserializer");
            }
            return values;
        }
    }
}
