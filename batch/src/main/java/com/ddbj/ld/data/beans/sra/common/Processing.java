package com.ddbj.ld.data.beans.sra.common;

import com.ddbj.ld.data.beans.sra.experiment.ExperimentConverter;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Processing {
    private Pipeline pipeline;
    private SequencingDirectives directives;

    @JsonProperty("PIPELINE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Pipeline getPipeline() { return pipeline; }
    @JsonProperty("PIPELINE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPipeline(Pipeline value) { this.pipeline = value; }

    @JsonProperty("DIRECTIVES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Processing.SequencingDirectivesDeserializer.class)
    public SequencingDirectives getDirectives() { return directives; }
    @JsonProperty("DIRECTIVES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Processing.SequencingDirectivesDeserializer.class)
    public void setDirectives(SequencingDirectives value) { this.directives = value; }

    static class SequencingDirectivesDeserializer extends JsonDeserializer<SequencingDirectives> {
        @Override
        public SequencingDirectives deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            SequencingDirectives value = null;
            // FIXME
            var mapper = ExperimentConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    // 空文字があるため、削除
                    break;
                case START_OBJECT:
                    value = mapper.readValue(jsonParser, SequencingDirectives.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize DIRECTIVES");
            }
            return value;
        }
    }
}
