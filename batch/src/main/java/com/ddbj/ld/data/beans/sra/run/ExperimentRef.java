package com.ddbj.ld.data.beans.sra.run;

import com.ddbj.ld.data.beans.sra.common.Identifiers;
import com.ddbj.ld.data.beans.sra.common.ReadSpec;
import com.ddbj.ld.data.beans.sra.common.RelativeOrder;
import com.ddbj.ld.data.beans.sra.experiment.ExperimentConverter;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ExperimentRef {
    private String refname;
    private String refcenter;
    private String accession;
    private Identifiers identifiers;

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

    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExperimentRef.IdentifiersDeserializer.class)
    public Identifiers getIdentifiers() { return identifiers; }
    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExperimentRef.IdentifiersDeserializer.class)
    public void setIdentifiers(Identifiers value) { this.identifiers = value; }

    static class IdentifiersDeserializer extends JsonDeserializer<Identifiers> {
        @Override
        public Identifiers deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Identifiers value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    // FIXME
                    var mapper = ExperimentConverter.getObjectMapper();
                    value = mapper.readValue(jsonParser, Identifiers.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize LocalID");
            }
            return value;
        }
    }
}
