package com.ddbj.ld.data.beans.sra.experiment;

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
public class Pool {
    private Member defaultMember;
    private List<Member> member;

    @JsonProperty("DEFAULT_MEMBER")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Member getDefaultMember() { return defaultMember; }
    @JsonProperty("DEFAULT_MEMBER")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDefaultMember(Member value) { this.defaultMember = value; }

    @JsonProperty("MEMBER")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Pool.MemberDeserializer.class)
    public List<Member> getMember() { return member; }
    @JsonProperty("MEMBER")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Pool.MemberDeserializer.class)
    public void setMember(List<Member> value) { this.member = value; }

    static class MemberDeserializer extends JsonDeserializer<List<Member>> {
        @Override
        public List<Member> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Member>();
            // FIXME
            var mapper = ExperimentConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Member>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Member.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize MEMBER");
            }
            return values;
        }
    }
}
