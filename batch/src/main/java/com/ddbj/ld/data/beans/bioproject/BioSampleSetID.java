package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@JsonDeserialize(using = BioSampleSetID.Deserializer.class)
public class BioSampleSetID {
    private String localID;
    private String userID;
    private String db;
    private String content;

    @JsonProperty("local_id")
    public String getLocalID() { return localID; }
    @JsonProperty("local_id")
    public void setLocalID(String value) { this.localID = value; }

    @JsonProperty("user_id")
    public String getUserID() { return userID; }
    @JsonProperty("user_id")
    public void setUserID(String value) { this.userID = value; }

    @JsonProperty("db")
    public String getDB() { return db; }
    @JsonProperty("db")
    public void setDB(String value) { this.db = value; }

    @JsonProperty("content")
    public String getContent() { return content; }
    @JsonProperty("content")
    public void setContent(String value) { this.content = value; }

    static class Deserializer extends JsonDeserializer<BioSampleSetID> {
        @Override
        public BioSampleSetID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            BioSampleSetID value = new BioSampleSetID();

            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_OBJECT:
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var localId = null == map.get("local_id") ? null : map.get("local_id").toString();
                    var userId = null == map.get("user_id") ? null : map.get("user_id").toString();
                    var db = null == map.get("db") ? null : map.get("db").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setLocalID(localId);
                    value.setUserID(userId);
                    value.setDB(db);
                    value.setContent(content);

                    break;
                default:
                    log.error("Cannot deserialize ID");
            }
            return value;
        }
    }
}
