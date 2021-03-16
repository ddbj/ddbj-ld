package com.ddbj.ld.data.beans.dra.run;

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
public class RunLinks {
    private List<RunLink> runLink;

    @JsonProperty("RUN_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = RunLinks.RunLinkDeserializer.class)
    public List<RunLink> getRunLink() { return runLink; }
    @JsonProperty("RUN_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = RunLinks.RunLinkDeserializer.class)
    public void setRunLink(List<RunLink> value) { this.runLink = value; }

    static class RunLinkDeserializer extends JsonDeserializer<List<RunLink>> {
        @Override
        public List<RunLink> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<RunLink> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<RunLink>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, RunLink.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize IDDeserializer");
            }
            return values;
        }
    }
}
