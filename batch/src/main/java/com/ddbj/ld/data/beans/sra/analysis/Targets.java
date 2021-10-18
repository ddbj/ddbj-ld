package com.ddbj.ld.data.beans.sra.analysis;

import com.ddbj.ld.data.beans.sra.common.Identifiers;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Targets {
    private List<StudyRef> target;
    private Identifiers identifiers;

    @JsonProperty("TARGET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Targets.StudyRefDeserializer.class)
    public List<StudyRef> getTarget() { return target; }
    @JsonProperty("TARGET")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Targets.StudyRefDeserializer.class)
    public void setTarget(List<StudyRef> value) { this.target = value; }

    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Identifiers getIdentifiers() { return identifiers; }
    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIdentifiers(Identifiers value) { this.identifiers = value; }

    static class StudyRefDeserializer extends JsonDeserializer<List<StudyRef>> {
        @Override
        public List<StudyRef> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<StudyRef>();
            var mapper = AnalysisConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<StudyRef>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, StudyRef.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Targets.StudyRefDeserializer");
            }
            return values;
        }
    }
}