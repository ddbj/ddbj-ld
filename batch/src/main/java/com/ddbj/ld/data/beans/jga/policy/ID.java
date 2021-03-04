package com.ddbj.ld.data.beans.jga.policy;

import com.fasterxml.jackson.annotation.*;
<<<<<<< HEAD
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = ID.Deserializer.class)
@Slf4j
=======

>>>>>>> 取り込み、修正
public class ID {
    private String namespace;
    private String content;

    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getNamespace() { return namespace; }
    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setNamespace(String value) { this.namespace = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }
<<<<<<< HEAD

    static class Deserializer extends JsonDeserializer<ID> {
        @Override
        public ID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ID value = new ID();

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

                    var label = null == map.get("label") ? null : map.get("label").toString();
                    var namespace  = null == map.get("namespace") ? null : map.get("namespace").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setLabel(label);
                    value.setNamespace(namespace);
                    value.setContent(content);

                    break;
                default:
                    log.error("Cannot deserialize ID");
            }
            return value;
        }
    }
=======
>>>>>>> 取り込み、修正
}
