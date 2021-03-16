package com.ddbj.ld.data.beans.dra.study;

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
public class RelatedStudies {
    private List<RelatedStudy> relatedStudy;

    @JsonProperty("RELATED_STUDY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = RelatedStudies.RelatedStudyDeserializer.class)
    public List<RelatedStudy> getRelatedStudy() { return relatedStudy; }
    @JsonProperty("RELATED_STUDY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = RelatedStudies.RelatedStudyDeserializer.class)
    public void setRelatedStudy(List<RelatedStudy> value) { this.relatedStudy = value; }

    static class RelatedStudyDeserializer extends JsonDeserializer<List<RelatedStudy>> {
        @Override
        public List<RelatedStudy> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<RelatedStudy> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<RelatedStudy>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, RelatedStudy.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize RelatedStudyDeserializer");
            }
            return values;
        }
    }
}
