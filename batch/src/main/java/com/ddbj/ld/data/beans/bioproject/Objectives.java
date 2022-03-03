package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@Slf4j
public class Objectives {
    @XmlElement(name="Data")
    @JsonProperty("Data")
    @JsonDeserialize(using = Objectives.ObjectivesDataDeserializer.class)
    private List<ObjectivesData> data;

    static class ObjectivesDataDeserializer extends JsonDeserializer<List<ObjectivesData>> {
        @Override
        public List<ObjectivesData> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<ObjectivesData>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list= BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<ObjectivesData>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value= BioProjectConverter.getObjectMapper().readValue(jsonParser, ObjectivesData.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ObjectivesData");
            }
            return values;
        }
    }
}
