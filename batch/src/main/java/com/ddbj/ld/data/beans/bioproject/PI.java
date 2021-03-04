package com.ddbj.ld.data.beans.bioproject;

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

// FIXME ddbjのxmlにはない項目、要確認
@JsonDeserialize(using = PI.Deserializer.class)
@Slf4j
public class PI {
    private String affil;
    private String last;
    private String auth;
    private String grantUserId;
    private String first;
    private String middle;
    // FIXME grantUserIdとの表記ゆれ？
    private String userId;
    private String given;

    @JsonProperty("affil")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAffil() { return affil; }
    @JsonProperty("affil")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAffil(String value) { this.affil = value; }
    @JsonProperty("Last")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLast() { return last; }
    @JsonProperty("Last")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLast(String value) { this.last = value; }
    @JsonProperty("auth")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAuth() { return auth; }
    @JsonProperty("auth")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAuth(String value) { this.auth = value; }
    @JsonProperty("grant_user_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGrantUserId() { return grantUserId; }
    @JsonProperty("grant_user_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGrantUserId(String value) { this.grantUserId = value; }
    @JsonProperty("First")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFirst() { return first; }
    @JsonProperty("First")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setFirst(String value) { this.first = value; }
    @JsonProperty("Middle")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMiddle() { return middle; }
    @JsonProperty("Middle")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMiddle(String value) { this.middle = value; }
    @JsonProperty("userid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUserId() { return userId; }
    @JsonProperty("userid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setUserId(String value) { this.userId = value; }
    @JsonProperty("Given")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGiven() { return given; }
    @JsonProperty("Given")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setGiven(String value) { this.given = value; }

    static class Deserializer extends JsonDeserializer<PI> {
        @Override
        public PI deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
            PI value = new PI();

            try {
                switch (jsonParser.currentToken()) {
                    case VALUE_NULL:
                        break;
                    case START_OBJECT:
                        Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                        var affil = null == map.get("affil") ? null : map.get("affil").toString();
                        var last  = null == map.get("Last") ? null : map.get("Last").toString();
                        var auth  = null == map.get("auth") ? null : map.get("auth").toString();
                        var grantUserId  = null == map.get("grant_user_id") ? null : map.get("grant_user_id").toString();
                        var first  = null == map.get("First") ? null : map.get("First").toString();
                        var middle = null == map.get("Middle") ? null : map.get("Middle").toString();
                        var userId = null == map.get("userid") ? null : map.get("userid").toString();
                        var given = null == map.get("Given") ? null : map.get("Given").toString();

                        value.setAffil(affil);
                        value.setLast(last);
                        value.setAuth(auth);
                        value.setGrantUserId(grantUserId);
                        value.setFirst(first);
                        value.setMiddle(middle);
                        value.setUserId(userId);
                        value.setGiven(given);

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
