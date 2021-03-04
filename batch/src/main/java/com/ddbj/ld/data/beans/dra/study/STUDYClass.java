package com.ddbj.ld.data.beans.dra.study;

<<<<<<< HEAD
import com.ddbj.ld.data.beans.dra.common.Identifiers;
import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown=true)
=======
import com.fasterxml.jackson.annotation.*;

>>>>>>> 取り込み、修正
public class STUDYClass {
    private String alias;
    private String centerName;
    private String brokerName;
    private String accession;
    private Identifiers identifiers;
    private Descriptor descriptor;
    private StudyLinks studyLinks;
    private StudyAttributes studyAttributes;

    @JsonProperty("alias")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAlias() { return alias; }
    @JsonProperty("alias")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAlias(String value) { this.alias = value; }

    @JsonProperty("center_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCenterName() { return centerName; }
    @JsonProperty("center_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setCenterName(String value) { this.centerName = value; }

    @JsonProperty("broker_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBrokerName() { return brokerName; }
    @JsonProperty("broker_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBrokerName(String value) { this.brokerName = value; }

    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccession() { return accession; }
    @JsonProperty("accession")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccession(String value) { this.accession = value; }

    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Identifiers getIdentifiers() { return identifiers; }
    @JsonProperty("IDENTIFIERS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIdentifiers(Identifiers value) { this.identifiers = value; }

    @JsonProperty("DESCRIPTOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Descriptor getDescriptor() { return descriptor; }
    @JsonProperty("DESCRIPTOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescriptor(Descriptor value) { this.descriptor = value; }

    @JsonProperty("STUDY_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyLinks getStudyLinks() { return studyLinks; }
    @JsonProperty("STUDY_LINKS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyLinks(StudyLinks value) { this.studyLinks = value; }

    @JsonProperty("STUDY_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyAttributes getStudyAttributes() { return studyAttributes; }
    @JsonProperty("STUDY_ATTRIBUTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStudyAttributes(StudyAttributes value) { this.studyAttributes = value; }
}
