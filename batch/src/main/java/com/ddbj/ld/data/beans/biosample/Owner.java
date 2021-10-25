package com.ddbj.ld.data.beans.biosample;

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
public class Owner {
    private List<NameElement> name;
    private Contacts contacts;

    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Owner.NameElementDeserializer.class)
    public List<NameElement> getName() { return name; }
    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Owner.NameElementDeserializer.class)
    public void setName(List<NameElement> value) { this.name = value; }

    @JsonProperty("Contacts")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Contacts getContacts() { return contacts; }
    @JsonProperty("Contacts")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContacts(Contacts value) { this.contacts = value; }

    static class NameElementDeserializer extends JsonDeserializer<List<NameElement>> {
        @Override
        public List<NameElement> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String content;
            NameElement value;
            var values = new ArrayList<NameElement>();
            var mapper = Converter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    content = mapper.readValue(jsonParser, Float.class).toString();
                    value = new NameElement();
                    value.setContent(content);
                    values.add(value);

                    break;
                case VALUE_STRING:
                    content = mapper.readValue(jsonParser, String.class);
                    value = new NameElement();
                    value.setContent(content);
                    values.add(value);

                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<NameElement>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value = mapper.readValue(jsonParser, NameElement.class);
                    values.add(value);

                    break;
                case VALUE_NULL:
                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Owner.NameElementDeserializer");
            }
            return values;
        }
    }
}