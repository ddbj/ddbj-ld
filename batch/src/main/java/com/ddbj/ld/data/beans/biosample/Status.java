package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Status {
    private String when;
    private String status;

    @JsonProperty("when")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getWhen() { return when; }
    @JsonProperty("when")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setWhen(String value) { this.when = value; }

    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Status.StatusDeserializer.class)
    public String getStatus() { return status; }
    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Status.StatusDeserializer.class)
    public void setStatus(String value) { this.status = value; }

    static class StatusDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = new String();

            var mapper = Converter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case START_OBJECT:
                    value = jsonParser.readValueAs(String.class);

                    break;
                case VALUE_STRING:
                    value = jsonParser.readValueAs(String.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Status.StatusDeserializer");
            }
            return value;
        }
    }
}
