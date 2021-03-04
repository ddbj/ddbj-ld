package com.ddbj.ld.data.beans.dra.experiment;

<<<<<<< HEAD
import com.ddbj.ld.data.beans.dra.common.Link;
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
    private List<Link> link;

    @JsonProperty("EXPERIMENT_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExperimentLinks.LinkDeserializer.class)
    public List<Link> getExperimentLink() { return link; }
    @JsonProperty("EXPERIMENT_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExperimentLinks.LinkDeserializer.class)
    public void setExperimentLink(List<Link> value) { this.link = value; }

    static class LinkDeserializer extends JsonDeserializer<List<Link>> {
        @Override
        public List<Link> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Link> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Link>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Link.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ExperimentLinks.LinkDeserializer");
            }
            return values;
        }
    }
=======
import com.fasterxml.jackson.annotation.*;

public class ExperimentLinks {
    private ExperimentLink experimentLink;

    @JsonProperty("EXPERIMENT_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ExperimentLink getExperimentLink() { return experimentLink; }
    @JsonProperty("EXPERIMENT_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExperimentLink(ExperimentLink value) { this.experimentLink = value; }
>>>>>>> 取り込み、修正
}
