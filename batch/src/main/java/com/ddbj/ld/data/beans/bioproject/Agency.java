package com.ddbj.ld.data.beans.bioproject;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonProperty;
=======
import com.fasterxml.jackson.annotation.*;
>>>>>>> 取り込み、修正
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = Agency.Deserializer.class)
@Slf4j
public class Agency {
    private String abbr;
    private String content;

    @JsonProperty("abbr")
<<<<<<< HEAD
    public String getAbbr() { return abbr; }
    @JsonProperty("abbr")
    public void setAbbr(String value) { this.abbr = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    // FIXME ここにIntegerが入ってくる可能性
    public String getAbbr() { return abbr; }
    @JsonProperty("abbr")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAbbr(String value) { this.abbr = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setContent(String value) { this.content = value; }

    static class Deserializer extends JsonDeserializer<Agency> {
        @Override
        public Agency deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
<<<<<<< HEAD
            var value = new Agency();
=======
            Agency value = new Agency();
>>>>>>> 取り込み、修正

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

                    var abbr = null == map.get("abbr") ? null : map.get("abbr").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setAbbr(abbr);
                    value.setContent(content);

                    break;
                default:
<<<<<<< HEAD
                    log.error("Cannot deserialize PrimaryID");
=======
                    log.error("Cannot deserialize Agency");
>>>>>>> 取り込み、修正
            }
            return value;
        }
    }
}
