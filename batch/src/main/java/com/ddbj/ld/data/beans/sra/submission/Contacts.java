package com.ddbj.ld.data.beans.sra.submission;

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
public class Contacts {
    private List<Contact> contact;

    @JsonProperty("CONTACT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Contacts.ContactDeserializer.class)
    public List<Contact> getContact() { return contact; }
    @JsonProperty("CONTACT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Contacts.ContactDeserializer.class)
    public void setContact(List<Contact> value) { this.contact = value; }

    static class ContactDeserializer extends JsonDeserializer<List<Contact>> {
        @Override
        public List<Contact> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Contact>();
            var mapper = SubmissionConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Contact>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Contact.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Contacts.ContactDeserializer");
            }
            return values;
        }
    }
}
