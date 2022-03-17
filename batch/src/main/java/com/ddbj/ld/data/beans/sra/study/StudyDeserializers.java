package com.ddbj.ld.data.beans.sra.study;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StudyDeserializers {

    static class RelatedStudyListDeserializer extends JsonDeserializer<List<RelatedStudy>> {
        @Override
        public List<RelatedStudy> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<RelatedStudy>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    var list = StudyConverter.mapper.readValue(jsonParser, new TypeReference<List<RelatedStudy>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = StudyConverter.mapper.readValue(jsonParser, RelatedStudy.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize RelatedStudy list.");
            }
            return values;
        }
    }
}
