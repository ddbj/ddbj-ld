package com.ddbj.ld.data.beans.dra.experiment;

import com.ddbj.ld.data.beans.dra.analysis.File;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Pipeline {
    private List<PipeSection> pipeSection;

    @JsonProperty("PIPE_SECTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<PipeSection> getPipeSection() { return pipeSection; }
    @JsonProperty("PIPE_SECTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPipeSection(List<PipeSection> value) { this.pipeSection = value; }

    static class PipeSectionDeserializer extends JsonDeserializer<List<PipeSection>> {
        @Override
        public List<PipeSection> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<PipeSection> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<PipeSection>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, PipeSection.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize PipeSectionDeserializer");
            }
            return values;
        }
    }
}
