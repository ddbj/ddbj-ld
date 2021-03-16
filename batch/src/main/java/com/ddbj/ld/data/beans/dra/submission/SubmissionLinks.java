package com.ddbj.ld.data.beans.dra.submission;

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
public class SubmissionLinks {
    private List<SubmissionLink> submissionLink;

    @JsonProperty("SUBMISSION_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SubmissionLinks.SubmissionLinkDeserializer.class)
    public List<SubmissionLink> getSubmissionLink() { return submissionLink; }
    @JsonProperty("SUBMISSION_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SubmissionLinks.SubmissionLinkDeserializer.class)
    public void setSubmissionLink(List<SubmissionLink> value) { this.submissionLink = value; }

    static class SubmissionLinkDeserializer extends JsonDeserializer<List<SubmissionLink>> {
        @Override
        public List<SubmissionLink> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<SubmissionLink> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<SubmissionLink>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, SubmissionLink.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize SubmissionLinkDeserializer");
            }
            return values;
        }
    }
}
