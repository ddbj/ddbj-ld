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
public class SubmissionAttributes {
    private List<SubmissionAttribute> submissionAttribute;

    @JsonProperty("SUBMISSION_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SubmissionAttributes.SubmissionAttributeDeserializer.class)
    public List<SubmissionAttribute> getSubmissionAttribute() { return submissionAttribute; }
    @JsonProperty("SUBMISSION_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SubmissionAttributes.SubmissionAttributeDeserializer.class)
    public void setSubmissionAttribute(List<SubmissionAttribute> value) { this.submissionAttribute = value; }

    static class SubmissionAttributeDeserializer extends JsonDeserializer<List<SubmissionAttribute>> {
        @Override
        public List<SubmissionAttribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<SubmissionAttribute> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<SubmissionAttribute>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, SubmissionAttribute.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize IDDeserializer");
            }
            return values;
        }
    }
}
