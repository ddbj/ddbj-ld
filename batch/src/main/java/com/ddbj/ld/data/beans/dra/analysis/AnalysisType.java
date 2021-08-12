package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = AnalysisType.AnalysisTypeDeserializer.class)
@Slf4j
public class AnalysisType {
    private AbundanceMeasurement deNovoAssembly;
    private ReferenceAlignment referenceAlignment;
    private AbundanceMeasurement sequenceAnnotation;
    private AbundanceMeasurement abundanceMeasurement;

    @JsonProperty("DE_NOVO_ASSEMBLY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AbundanceMeasurement getDeNovoAssembly() { return deNovoAssembly; }
    @JsonProperty("DE_NOVO_ASSEMBLY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDeNovoAssembly(AbundanceMeasurement value) { this.deNovoAssembly = value; }

    @JsonProperty("REFERENCE_ALIGNMENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ReferenceAlignment getReferenceAlignment() { return referenceAlignment; }
    @JsonProperty("REFERENCE_ALIGNMENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setReferenceAlignment(ReferenceAlignment value) { this.referenceAlignment = value; }

    @JsonProperty("SEQUENCE_ANNOTATION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AbundanceMeasurement getSequenceAnnotation() { return sequenceAnnotation; }
    @JsonProperty("SEQUENCE_ANNOTATION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSequenceAnnotation(AbundanceMeasurement value) { this.sequenceAnnotation = value; }

    @JsonProperty("ABUNDANCE_MEASUREMENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AbundanceMeasurement getAbundanceMeasurement() { return abundanceMeasurement; }
    @JsonProperty("ABUNDANCE_MEASUREMENT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAbundanceMeasurement(AbundanceMeasurement value) { this.abundanceMeasurement = value; }

    static class AnalysisTypeDeserializer extends JsonDeserializer<AnalysisType> {
        @Override
        public AnalysisType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            AnalysisType value = new AnalysisType();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    break;
                case VALUE_STRING:
                    break;
                case START_OBJECT:
                    Map<String, Object> map = jsonParser.readValueAs(LinkedHashMap.class);

                    var dNAObj = map2Obj(map, "DE_NOVO_ASSEMBLY", AbundanceMeasurement.class);
                    var rAObj = map2Obj(map, "REFERENCE_ALIGNMENT", ReferenceAlignment.class);
                    var sAObj = map2Obj(map, "SEQUENCE_ANNOTATION", AbundanceMeasurement.class);
                    var aMObj = map2Obj(map, "ABUNDANCE_MEASUREMENT", AbundanceMeasurement.class);

                    value.setDeNovoAssembly(dNAObj);
                    value.setReferenceAlignment(rAObj);
                    value.setSequenceAnnotation(sAObj);
                    value.setAbundanceMeasurement(aMObj);

                    errorMsg(value, deserializationContext);
                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ID.IDDeserializer");
            }
            return value;
        }

        private void errorMsg(AnalysisType value, DeserializationContext dctx) throws IOException{
            Map<String, Object> dctxMap = dctx.getParser().readValueAs(LinkedHashMap.class);
            var accession = getMapObject(dctxMap, "accession");
            StringBuilder sb = new StringBuilder();
            sb.append("accession : ").append(accession);
            boolean comma = false;
            if (value.getDeNovoAssembly() == null) { sb.append(" DE_NOVO_ASSEMBLY, ");}
            if (value.getReferenceAlignment() == null) { sb.append(" REFERENCE_ALIGNMENT, ");}
            if (value.getSequenceAnnotation() == null) { sb.append(" SEQUENCE_ANNOTATION, ");}
            if (value.getAbundanceMeasurement() == null) { sb.append(" ABUNDANCE_MEASUREMENT, ");}
            sb.append(" does not allow nulls");
            log.info(sb.toString());

        }

        private <T> T map2Obj(Map<String, Object> map, String key, Class<T> value) throws JsonProcessingException {
            Object mapObj = getMapObject(map, key);
            var obj = map2Obj((Map<String, Object>) mapObj, value);

            return obj;
        }

        private Object getMapObject(Map<String, Object> map, String Key) {
            if (null != map.get(Key)) {
                return map.get(Key);
            }

            return null;
        }

        private <T> T map2Obj(Map<String, Object> map, Class<T> value) throws JsonProcessingException {
            var mapper = new ObjectMapper();

            String json = mapper.writeValueAsString(map);
            var obj = mapper.readValue(json, value);

            return obj;
        }
    }
}
