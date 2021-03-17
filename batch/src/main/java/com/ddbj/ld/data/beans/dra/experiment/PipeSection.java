package com.ddbj.ld.data.beans.dra.experiment;

import com.ddbj.ld.data.beans.dra.sample.SampleAttribute;
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

import static com.fasterxml.jackson.core.JsonToken.VALUE_STRING;

@Slf4j
public class PipeSection {
    private String sectionName;
    private String stepIndex;
    private List<String> prevStepIndex;
    private String program;
    private String version;
    private String notes;

    @JsonProperty("section_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSectionName() { return sectionName; }
    @JsonProperty("section_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSectionName(String value) { this.sectionName = value; }

    @JsonProperty("STEP_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStepIndex() { return stepIndex; }
    @JsonProperty("STEP_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStepIndex(String value) { this.stepIndex = value; }

    @JsonProperty("PREV_STEP_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = PipeSection.Deserializer.class)
    public List<String> getPrevStepIndex() { return prevStepIndex; }
    @JsonProperty("PREV_STEP_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = PipeSection.Deserializer.class)
    public void setPrevStepIndex(List<String> value) { this.prevStepIndex = value; }

    @JsonProperty("PROGRAM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getProgram() { return program; }
    @JsonProperty("PROGRAM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProgram(String value) { this.program = value; }

    @JsonProperty("VERSION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getVersion() { return version; }
    @JsonProperty("VERSION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setVersion(String value) { this.version = value; }

    @JsonProperty("NOTES")
    public String getNotes() { return notes; }
    @JsonProperty("NOTES")
    public void setNotes(String value) { this.notes = value; }

    static class Deserializer extends JsonDeserializer<List<String>> {
        @Override
        public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<String> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();
            mapper.coercionConfigFor(Processing.class).setAcceptBlankAsEmpty(true);
            var token = jsonParser.currentToken() == null ? VALUE_STRING : jsonParser.currentToken();

            switch (token) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    values.add(jsonParser.readValueAs(Integer.class).toString());

                    break;
                case VALUE_STRING:
                    values.add("");
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<String>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, String.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize String Deserializer");
            }
            return values;
        }
    }
}
