package com.ddbj.ld.data.beans.dra.analysis;

<<<<<<< HEAD
import com.ddbj.ld.data.beans.dra.common.XrefLink;
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
public class Standard {
    private String shortName;
    private List<XrefLink> name;
=======
import com.fasterxml.jackson.annotation.*;

public class Standard {
    private String shortName;
    private XrefLink name;
>>>>>>> 取り込み、修正

    @JsonProperty("short_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getShortName() { return shortName; }
    @JsonProperty("short_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setShortName(String value) { this.shortName = value; }

    @JsonProperty("NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
<<<<<<< HEAD
    @JsonDeserialize(using = Standard.XrefLinkDeserializer.class)
    public List<XrefLink> getName() { return name; }
    @JsonProperty("NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Standard.XrefLinkDeserializer.class)
    public void setName(List<XrefLink> value) { this.name = value; }

    static class XrefLinkDeserializer extends JsonDeserializer<List<XrefLink>> {
        @Override
        public List<XrefLink> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<XrefLink> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();
            mapper.coercionConfigFor(XrefLink.class).setAcceptBlankAsEmpty(true);

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<XrefLink>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, XrefLink.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Standard.XrefLinkDeserializer");
            }
            return values;
        }
    }
=======
    public XrefLink getName() { return name; }
    @JsonProperty("NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(XrefLink value) { this.name = value; }
>>>>>>> 取り込み、修正
}
