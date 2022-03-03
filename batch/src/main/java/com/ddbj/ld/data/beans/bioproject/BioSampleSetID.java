package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@XmlAccessorType(XmlAccessType.FIELD)
@JsonDeserialize(using = BioSampleSetID.Deserializer.class)
@Data
public class BioSampleSetID {
    @XmlAttribute(name="local_id")
    @JsonProperty("local_id")
    private String localID;

    @XmlAttribute(name="user_id")
    @JsonProperty("user_id")
    private String userID;

    @XmlAttribute(name="db")
    @JsonProperty("db")
    private String db;

    @XmlValue
    @JsonProperty("content")
    private String content;

    static class Deserializer extends JsonDeserializer<BioSampleSetID> {
        @Override
        public BioSampleSetID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            BioSampleSetID value = new BioSampleSetID();

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

                    var localId = null == map.get("local_id") ? null : map.get("local_id").toString();
                    var userId = null == map.get("user_id") ? null : map.get("user_id").toString();
                    var db = null == map.get("db") ? null : map.get("db").toString();
                    var content = null == map.get("content") ? null : map.get("content").toString();

                    value.setLocalID(localId);
                    value.setUserID(userId);
                    value.setDb(db);
                    value.setContent(content);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ID");
            }
            return value;
        }
    }
}
