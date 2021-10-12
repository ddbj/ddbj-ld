package com.ddbj.ld.data.beans.sra.study;

import com.ddbj.ld.data.beans.sra.common.Link;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@JsonIgnoreProperties(ignoreUnknown=true) // xmlns:comを無視する
public class StudyLinks {
    private List<Link> studyLink;

    @JsonProperty("STUDY_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = StudyLinks.LinkDeserializer.class)
    public List<Link> getStudyLink() { return studyLink; }
    @JsonProperty("STUDY_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = StudyLinks.LinkDeserializer.class)
    public void setStudyLink(List<Link> value) { this.studyLink = value; }

    static class LinkDeserializer extends JsonDeserializer<List<Link>> {
        @Override
        public List<Link> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Link>();
            var mapper = StudyConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
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
                    log.error("Cannot deserialize StudyLinks.LinkDeserializer");
            }
            return values;
        }
    }
}
