package com.ddbj.ld.data.beans.sra.analysis;

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
public class SeqLabels {
    private List<Sequence> sequence;

    @JsonProperty("SEQUENCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SeqLabels.SequenceDeserializer.class)
    public List<Sequence> getSequence() { return sequence; }
    @JsonProperty("SEQUENCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SeqLabels.SequenceDeserializer.class)
    public void setSequence(List<Sequence> value) { this.sequence = value; }

    static class SequenceDeserializer extends JsonDeserializer<List<Sequence>> {
        @Override
        public List<Sequence> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Sequence>();
            // FIXME
            var mapper = AnalysisConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Sequence>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Sequence.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize SEQUENCE");
            }
            return values;
        }
    }
}
