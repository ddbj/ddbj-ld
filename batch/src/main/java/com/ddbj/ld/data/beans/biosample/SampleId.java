package com.ddbj.ld.data.beans.biosample;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class SampleId {
    private String namespace;
    private String otherNamespace;
    private String isPrimary;
    private String content;
    private String db; // 関連リソースへのリンク. 格納
    private String dblabel; // 関連リソースへのリンク. 格納
    private String ishidden; // アクセス制限サンプル. suppressed のフラグ? 格納

    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getNamespace() { return namespace; }
    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setNamespace(String value) { this.namespace = value; }

    @JsonProperty("other_namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOtherNamespace() { return otherNamespace; }
    @JsonProperty("other_namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOtherNamespace(String value) { this.otherNamespace = value; }

    @JsonProperty("is_primary")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SampleId.StringDeserializer.class)
    public String getIsPrimary() { return isPrimary; }
    @JsonProperty("is_primary")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SampleId.StringDeserializer.class)
    public void setIsPrimary(String value) { this.isPrimary = value; }

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getContent() { return content; }
    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContent(String value) { this.content = value; }

    // TODO: 実データに存在するがXSDに定義が無いため要仕様確認
    @JsonProperty("db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDB() { return db; }
    @JsonProperty("db")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDB(String value) { this.db = value; }

    // TODO: 実データに存在するがXSDに定義が無いため要仕様確認
    @JsonProperty("db_label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDBLabel() { return dblabel; }
    @JsonProperty("db_label")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDBLabel(String value) { this.dblabel = value; }

    // TODO: 実データに存在するがXSDに定義が無いため要仕様確認
    @JsonProperty("is_hidden")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIsHidden() { return ishidden; }
    @JsonProperty("is_hidden")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIsHidden(String value) { this.ishidden = value; }

    static class StringDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = new String();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value = jsonParser.readValueAs(Integer.class).toString();
                    break;
                case VALUE_STRING:
                    value = jsonParser.readValueAs(String.class);
                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize SampleId.StringDeserializer");
            }
            return value;
        }
    }
}