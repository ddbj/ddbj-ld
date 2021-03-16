package com.ddbj.ld.data.beans.dra.experiment;

import com.ddbj.ld.data.beans.dra.analysis.PrimaryID;
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
public class ExperimentLinks {
    private List<ExperimentLink> experimentLink;

    @JsonProperty("EXPERIMENT_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExperimentLinks.ExperimentLinkDeserializer.class)
    public List<ExperimentLink> getExperimentLink() { return experimentLink; }
    @JsonProperty("EXPERIMENT_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExperimentLinks.ExperimentLinkDeserializer.class)
    public void setExperimentLink(List<ExperimentLink> value) { this.experimentLink = value; }

    static class ExperimentLinkDeserializer extends JsonDeserializer<List<ExperimentLink>> {
        @Override
        public List<ExperimentLink> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<ExperimentLink> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<ExperimentLink>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, ExperimentLink.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize ExperimentLinkDeserializer");
            }
            return values;
        }
    }
}
