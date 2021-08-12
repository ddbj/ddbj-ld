package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = LocalID.Deserializer.class)
@Slf4j
public class LocalID {
    private String submissionID;
    private String content;

    @JsonProperty("submission_id")
    public String getSubmissionID() { return submissionID; }
    @JsonProperty("submission_id")
    public void setSubmissionID(String value) { this.submissionID = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }

    static class Deserializer extends JsonDeserializer<LocalID> {
        @Override
        public LocalID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var value = new LocalID();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var abbr = null == map.get("submission_id") ? null : map.get("submission_id").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setSubmissionID(abbr);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize LocalID");
            }
            return value;
        }
    }
}
