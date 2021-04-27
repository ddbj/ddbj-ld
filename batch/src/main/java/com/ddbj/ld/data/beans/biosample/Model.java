package com.ddbj.ld.data.beans.biosample;

import com.ddbj.ld.data.beans.dra.common.ID;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = Model.ModelDeserializer.class)
@Slf4j
public class Model {
    private String version;
    private String content;

    @JsonProperty("version")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getVersion() { return version; }
    @JsonProperty("version")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setVersion(String value) { this.version = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }

    static class ModelDeserializer extends JsonDeserializer<Model> {
        @Override
        public Model deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Model value = new Model();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    value.setContent(jsonParser.readValueAs(String.class));
                    break;
                case START_OBJECT:
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var version = null == map.get("version") ? null : map.get("version").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setVersion(version);
                    value.setContent(content);

                    break;
                default:
                    log.error("Cannot deserialize Model.ModelDeserializer");
            }
            return value;
        }
    }
}
