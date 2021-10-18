package com.ddbj.ld.data.beans.sra.experiment;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@JsonIgnoreProperties(ignoreUnknown=true)
public class LibraryLayout {
    private Paired paired;

    @JsonProperty("PAIRED")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = LibraryLayout.PairedDeserializer.class)
    public Paired getPaired() { return paired; }
    @JsonProperty("PAIRED")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = LibraryLayout.PairedDeserializer.class)
    public void setPaired(Paired value) { this.paired = value; }

    static class PairedDeserializer extends JsonDeserializer<Paired> {
        @Override
        public Paired deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Paired value = new Paired();
            var mapper = ExperimentConverter.getObjectMapper();
            mapper.coercionConfigFor(Paired.class).setAcceptBlankAsEmpty(true);

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = mapper.readValue(jsonParser, Paired.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize LibraryLayout.PairedDeserializer");
            }
            return value;
        }
    }
}