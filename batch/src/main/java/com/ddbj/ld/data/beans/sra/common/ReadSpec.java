package com.ddbj.ld.data.beans.sra.common;

import com.ddbj.ld.data.beans.bioproject.LocalID;
import com.ddbj.ld.data.beans.sra.experiment.ExperimentConverter;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class ReadSpec {
    private String readIndex;
    private String readLabel;
    private String readClass;
    private String readType;
    private RelativeOrder relativeOrder;
    private String baseCoord;
    private ExpectedBasecallTable expectedBasecallTable;

    @JsonProperty("READ_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadIndex() { return readIndex; }
    @JsonProperty("READ_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadIndex(String value) { this.readIndex = value; }

    @JsonProperty("READ_LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadLabel() { return readLabel; }
    @JsonProperty("READ_LABEL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadLabel(String value) { this.readLabel = value; }

    @JsonProperty("READ_CLASS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadClass() { return readClass; }
    @JsonProperty("READ_CLASS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadClass(String value) { this.readClass = value; }

    @JsonProperty("READ_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReadType() { return readType; }
    @JsonProperty("READ_TYPE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReadType(String value) { this.readType = value; }

    // FIXME
    @JsonProperty("RELATIVE_ORDER")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ReadSpec.RelativeOrderDeserializer.class)
    public RelativeOrder getRelativeOrder() { return relativeOrder; }
    @JsonProperty("RELATIVE_ORDER")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ReadSpec.RelativeOrderDeserializer.class)
    public void setRelativeOrder(RelativeOrder value) { this.relativeOrder = value; }

    @JsonProperty("BASE_COORD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBaseCoord() { return baseCoord; }
    @JsonProperty("BASE_COORD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBaseCoord(String value) { this.baseCoord = value; }

    @JsonProperty("EXPECTED_BASECALL_TABLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ExpectedBasecallTable getExpectedBasecallTable() { return expectedBasecallTable; }
    @JsonProperty("EXPECTED_BASECALL_TABLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setExpectedBasecallTable(ExpectedBasecallTable value) { this.expectedBasecallTable = value; }

    static class RelativeOrderDeserializer extends JsonDeserializer<RelativeOrder> {
        @Override
        public RelativeOrder deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            RelativeOrder value = null;

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    // FIXME
                    var mapper = ExperimentConverter.getObjectMapper();
                    value = mapper.readValue(jsonParser, RelativeOrder.class);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize RELATIVE_ORDER");
            }
            return value;
        }
    }
}
