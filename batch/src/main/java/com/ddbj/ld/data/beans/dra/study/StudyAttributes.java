package com.ddbj.ld.data.beans.dra.study;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StudyAttributes {
    private List<StudyAttribute> studyAttribute;

    @JsonProperty("STUDY_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = StudyAttributes.StudyAttributeDeserializer.class)
    public List<StudyAttribute> getStudyAttribute() { return studyAttribute; }
    @JsonProperty("STUDY_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = StudyAttributes.StudyAttributeDeserializer.class)
    public void setStudyAttribute(List<StudyAttribute> value) { this.studyAttribute = value; }

    static class StudyAttributeDeserializer extends JsonDeserializer<List<StudyAttribute>> {
        @Override
        public List<StudyAttribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<StudyAttribute> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<StudyAttribute>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, StudyAttribute.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize StudyAttributeDeserializer");
            }
            return values;
        }
    }
}
