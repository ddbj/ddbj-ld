package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;
<<<<<<< HEAD
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@JsonIgnoreProperties(ignoreUnknown=true)
=======

>>>>>>> 取り込み、修正
public class LibraryLayout {
    private Paired paired;

    @JsonProperty("PAIRED")
    @JsonInclude(JsonInclude.Include.NON_NULL)
<<<<<<< HEAD
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
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize LibraryLayout.PairedDeserializer");
            }
            return value;
        }
    }
=======
    public Paired getPaired() { return paired; }
    @JsonProperty("PAIRED")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPaired(Paired value) { this.paired = value; }
>>>>>>> 取り込み、修正
}
