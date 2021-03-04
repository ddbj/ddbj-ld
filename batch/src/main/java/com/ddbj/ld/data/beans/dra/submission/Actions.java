package com.ddbj.ld.data.beans.dra.submission;

import com.fasterxml.jackson.annotation.*;
<<<<<<< HEAD
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
public class Actions {
    private List<Action> action;

    @JsonProperty("ACTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Actions.ActionsDeserializer.class)
    public List<Action> getAction() { return action; }
    @JsonProperty("ACTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Actions.ActionsDeserializer.class)
    public void setAction(List<Action> value) { this.action = value; }

    static class ActionsDeserializer extends JsonDeserializer<List<Action>> {
        @Override
        public List<Action> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Action> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Action>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Action.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Actions.ActionsDeserializer");
            }
            return values;
        }
    }
=======

public class Actions {
    private Action action;

    @JsonProperty("ACTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Action getAction() { return action; }
    @JsonProperty("ACTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAction(Action value) { this.action = value; }
>>>>>>> 取り込み、修正
}
