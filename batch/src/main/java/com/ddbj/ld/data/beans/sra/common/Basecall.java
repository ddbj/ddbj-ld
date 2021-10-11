package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = Basecall.BasecallDeserializer.class)
@Slf4j
public class Basecall {
    private String readGroupTag;
    private String minMatch;
    private String maxMismatch;
    private String matchEdge;
    private String content;

    @JsonProperty("read_group_tag")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadGroupTag() { return readGroupTag; }
    @JsonProperty("read_group_tag")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadGroupTag(String value) { this.readGroupTag = value; }

    @JsonProperty("min_match")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMinMatch() { return minMatch; }
    @JsonProperty("min_match")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMinMatch(String value) { this.minMatch = value; }

    @JsonProperty("max_mismatch")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMaxMismatch() { return maxMismatch; }
    @JsonProperty("max_mismatch")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMaxMismatch(String value) { this.maxMismatch = value; }

    @JsonProperty("match_edge")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMatchEdge() { return matchEdge; }
    @JsonProperty("match_edge")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMatchEdge(String value) { this.matchEdge = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }

    static class BasecallDeserializer extends JsonDeserializer<Basecall> {
        @Override
        public Basecall deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Basecall value = new Basecall();

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

                    var readGroupTag = null == map.get("read_group_tag") ? null : map.get("read_group_tag").toString();
                    var minMatch = null == map.get("min_match") ? null : map.get("min_match").toString();
                    var maxMismatch = null == map.get("max_mismatch") ? null : map.get("max_mismatch").toString();
                    var matchEdge = null == map.get("match_edge") ? null : map.get("match_edge").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setReadGroupTag(readGroupTag);
                    value.setMinMatch(minMatch);
                    value.setMaxMismatch(maxMismatch);
                    value.setMatchEdge(matchEdge);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Basecall.BasecallDeserializer");
            }
            return value;
        }
    }
}
