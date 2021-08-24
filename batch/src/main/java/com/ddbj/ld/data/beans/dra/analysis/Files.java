package com.ddbj.ld.data.beans.dra.analysis;

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
public class Files {
    private List<File> file;

    @JsonProperty("FILE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Files.FileDeserializer.class)
    public List<File> getFile() { return file; }
    @JsonProperty("FILE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Files.FileDeserializer.class)
    public void setFile(List<File> value) { this.file = value; }

    static class FileDeserializer extends JsonDeserializer<List<File>> {
        @Override
        public List<File> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<File>();
            var mapper = AnalysisConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<File>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, File.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Files.FileDeserializer");
            }
            return values;
        }
    }
}
