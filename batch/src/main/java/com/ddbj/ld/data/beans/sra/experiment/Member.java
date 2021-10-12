package com.ddbj.ld.data.beans.sra.experiment;

import com.ddbj.ld.data.beans.sra.common.Identifiers;
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
public class Member {
    private String refname;
    private String refcenter;
    private String accession;
    private String memberName;
    private String proportion;
    private Identifiers identifiers;
    private List<ReadLabel> readLabel;

    @JsonProperty("refname")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRefname() { return refname; }
    @JsonProperty("refname")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRefname(String value) { this.refname = value; }

    @JsonProperty("refcenter")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRefcenter() { return refcenter; }
    @JsonProperty("refcenter")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRefcenter(String value) { this.refcenter = value; }

    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccession() { return accession; }
    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccession(String value) { this.accession = value; }

    @JsonProperty("member_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMemberName() { return memberName; }
    @JsonProperty("member_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMemberName(String value) { this.memberName = value; }

    @JsonProperty("proportion")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getProportion() { return proportion; }
    @JsonProperty("proportion")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProportion(String value) { this.proportion = value; }

    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Identifiers getIdentifiers() { return identifiers; }
    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIdentifiers(Identifiers value) { this.identifiers = value; }

    @JsonProperty("READ_LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Member.ReadLabelDeserializer.class)
    public List<ReadLabel> getReadLabel() { return readLabel; }
    @JsonProperty("READ_LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Member.ReadLabelDeserializer.class)
    public void setReadLabel(List<ReadLabel> value) { this.readLabel = value; }

    static class ReadLabelDeserializer extends JsonDeserializer<List<ReadLabel>> {
        @Override
        public List<ReadLabel> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<ReadLabel>();
            // FIXME
            var mapper = ExperimentConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<ReadLabel>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, ReadLabel.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize READ_LABEL");
            }
            return values;
        }
    }
}
