package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Link {
    private String target;
    private String label;
    private String type;
    private String content;
//    private String submissionid; // NCBI submission管理用内部ID. skip

    @JsonProperty("target")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTarget() { return target; }
    @JsonProperty("target")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTarget(String value) { this.target = value; }

    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLabel() { return label; }
    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLabel(String value) { this.label = value; }

    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getType() { return type; }
    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setType(String value) { this.type = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Link.StringDeserializer.class)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Link.StringDeserializer.class)
    public void setContent(String value) { this.content = value; }

    static class StringDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = new String();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = jsonParser.readValueAs(Integer.class).toString();
                    break;
                case VALUE_STRING:
                    value = jsonParser.readValueAs(String.class);
                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Link.StringDeserializer");
            }
            return value;
        }
    }
}
