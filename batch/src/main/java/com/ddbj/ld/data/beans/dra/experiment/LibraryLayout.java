package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@JsonIgnoreProperties(ignoreUnknown=true)
public class LibraryLayout {
    private String single;
    private Paired paired;

    @JsonProperty("SINGLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSingle() { return single; }
    @JsonProperty("SINGLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSingle(String value) { this.single = value; }

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
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();
            mapper.coercionConfigFor(Paired.class).setAcceptBlankAsEmpty(true);

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = mapper.readValue(jsonParser, Paired.class);

                    break;
                default:
                    log.error("Cannot deserialize LibraryLayout.PairedDeserializer");
            }
            return value;
        }
    }
}
