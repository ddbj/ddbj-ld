package com.ddbj.ld.data.beans.sra.submission;

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
public class SubmissionLinks {
    private List<Link> submissionLink;

    @JsonProperty("SUBMISSION_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SubmissionLinks.LinkDeserializer.class)
    public List<Link> getSubmissionLink() { return submissionLink; }
    @JsonProperty("SUBMISSION_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SubmissionLinks.LinkDeserializer.class)
    public void setSubmissionLink(List<Link> value) { this.submissionLink = value; }

    static class LinkDeserializer extends JsonDeserializer<List<Link>> {
        @Override
        public List<Link> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Link>();
            var mapper = SubmissionConverter.getObjectMapper();

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
                    log.error("Cannot deserialize SubmissionLinks.LinkDeserializer");
            }
            return values;
        }
    }
}
