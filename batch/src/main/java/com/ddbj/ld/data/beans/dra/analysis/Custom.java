package com.ddbj.ld.data.beans.dra.analysis;

<<<<<<< HEAD
import com.ddbj.ld.data.beans.dra.common.Link;
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

@Slf4j
public class Custom {
    private String description;
    private List<Link> referenceSource;
=======
import com.fasterxml.jackson.annotation.*;

public class Custom {
    private String description;
    private AnalysisLink referenceSource;
>>>>>>> 取り込み、修正

    @JsonProperty("DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() { return description; }
    @JsonProperty("DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("REFERENCE_SOURCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
<<<<<<< HEAD
    @JsonDeserialize(using = Custom.LinkDeserializer.class)
    public List<Link> getReferenceSource() { return referenceSource; }
    @JsonProperty("REFERENCE_SOURCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Custom.LinkDeserializer.class)
    public void setReferenceSource(List<Link> value) { this.referenceSource = value; }

    static class LinkDeserializer extends JsonDeserializer<List<Link>> {
        @Override
        public List<Link> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Link> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();
            mapper.coercionConfigFor(Link.class).setAcceptBlankAsEmpty(true);

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Link>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Link.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Custom.LinkDeserializer");
            }
            return values;
        }
    }
=======
    public AnalysisLink getReferenceSource() { return referenceSource; }
    @JsonProperty("REFERENCE_SOURCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReferenceSource(AnalysisLink value) { this.referenceSource = value; }
>>>>>>> 取り込み、修正
}
