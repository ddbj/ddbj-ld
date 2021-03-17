package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class DataBlock {
    private String name;
    private String serial;
    private String member;
    private Files files;

    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() { return name; }
    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(String value) { this.name = value; }

    @JsonProperty("serial")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSerial() { return serial; }
    @JsonProperty("serial")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSerial(String value) { this.serial = value; }

    @JsonProperty("member")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMember() { return member; }
    @JsonProperty("member")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMember(String value) { this.member = value; }

    @JsonProperty("FILES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = DataBlock.FileDeserializer.class)
    public Files getFiles() { return files; }
    @JsonProperty("FILES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = DataBlock.FileDeserializer.class)
    public void setFiles(Files value) { this.files = value; }

    static class FileDeserializer extends JsonDeserializer<Files> {
        @Override
        public Files deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Files value = new Files();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();
            mapper.coercionConfigFor(Files.class).setAcceptBlankAsEmpty(true);

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    value = mapper.readValue(jsonParser, Files.class);

                    break;
                default:
                    log.error("Cannot deserialize FilesFileDeserializer");
            }
            return value;
        }
    }
}
