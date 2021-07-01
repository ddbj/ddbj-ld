package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class StudyAttributes {
    private List<StudyAttribute> studyAttribute;

    @JsonProperty("STUDY_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = StudyAttributes.Deserializer.class)
    public List<StudyAttribute> getStudyAttribute() { return studyAttribute; }
    @JsonProperty("STUDY_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = StudyAttributes.Deserializer.class)
    public void setStudyAttribute(List<StudyAttribute> value) { this.studyAttribute = value; }

    static class Deserializer extends JsonDeserializer<List<StudyAttribute>> {
        @Override
        public List<StudyAttribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<StudyAttribute> values = new ArrayList<>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var array = jsonParser.readValueAs(ArrayList.class);

                    for(var obj : array) {
                        var map = (LinkedHashMap<String, Object>)obj;
                        var tag = null == map.get("TAG") ? null : map.get("TAG").toString();
                        var value = null == map.get("VALUE") ? null : map.get("VALUE").toString();
                        var units = null == map.get("UNITS") ? null : map.get("UNITS").toString();

                        var studyAttribute = new StudyAttribute();
                        studyAttribute.setTag(tag);
                        studyAttribute.setValue(value);
                        studyAttribute.setUnits(units);

                        values.add(studyAttribute);
                    }

                    break;
                case START_OBJECT:
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var tag = null == map.get("TAG") ? null : map.get("TAG").toString();
                    var value = null == map.get("VALUE") ? null : map.get("VALUE").toString();
                    var units = null == map.get("UNITS") ? null : map.get("UNITS").toString();

                    var studyAttribute = new StudyAttribute();
                    studyAttribute.setTag(tag);
                    studyAttribute.setValue(value);
                    studyAttribute.setUnits(units);

                    values.add(studyAttribute);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Agency");
            }
            return values;
        }
    }
}
