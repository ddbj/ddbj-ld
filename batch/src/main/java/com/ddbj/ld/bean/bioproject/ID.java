package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;
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
public class ID {
    private String localID;
    private String userID;
    private String db;
    private String content;

    @JsonProperty("local_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocalID() { return localID; }
    @JsonProperty("local_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLocalID(String value) { this.localID = value; }

    @JsonProperty("user_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUserID() { return userID; }
    @JsonProperty("user_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUserID(String value) { this.userID = value; }

    @JsonProperty("db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDB() { return db; }
    @JsonProperty("db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDB(String value) { this.db = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }

    static class Deserializer extends JsonDeserializer<ID> {
        @Override
        public ID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
            ID value = new ID();

            try {
                switch (jsonParser.currentToken()) {
                    case VALUE_NULL:
                        break;
                    // FIXME 基本数値型が入ってくるが、稀に文字が入ってくるので文字列型としているが、問題があるなら数値型に戻し文字列型を弾く
                    case VALUE_NUMBER_INT:
                    case VALUE_STRING:
                        value.setContent(jsonParser.readValueAs(String.class));
                        break;
                    case START_OBJECT:
                        Map<String, String> map = jsonParser.readValueAs(LinkedHashMap.class);

                        value.setLocalID(map.get("local_id"));
                        value.setUserID(map.get("user_id"));
                        value.setDB(map.get("db"));
                        value.setContent(map.get("content"));

                        break;
                    default:
                        log.error("Cannot deserialize ID");
                }
                return value;
            } catch (IOException e) {
                log.error("Cannot parse ID");

                return null;
            }
        }
    }
}
