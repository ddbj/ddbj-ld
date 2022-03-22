package com.ddbj.ld.data.beans.sra.submission;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.ddbj.ld.data.beans.sra.common.Identifiers;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.time.OffsetDateTime;

@XmlRootElement(name = "SUBMISSION") // XMLのルートタグ
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SUBMISSIONClass implements IPropertiesBean {
    @XmlAttribute(name = "alias")
    @JsonProperty("alias")
    private String alias;

    @XmlAttribute(name = "center_name")
    @JsonProperty("center_name")
    private String centerName;

    @XmlAttribute(name = "broker_name")
    @JsonProperty("broker_name")
    private String brokerName;

    @XmlAttribute(name = "accession")
    @JsonProperty("accession")
    private String accession;

    @XmlAttribute(name = "submission_date")
    @JsonProperty("submission_date")
    private OffsetDateTime submissionDate;

    @XmlAttribute(name = "submission_comment")
    @JsonProperty("submission_comment")
    private String submissionComment;

    @XmlAttribute(name = "lab_name")
    @JsonProperty("lab_name")
    private String labName;

    @XmlElement(name = "IDENTIFIERS")
    @JsonProperty("IDENTIFIERS")
    private Identifiers identifiers;

    @XmlElement(name = "TITLE")
    @JsonProperty("TITLE")
    private String title;

    @XmlElement(name = "CONTACTS")
    @JsonProperty("CONTACTS")
    private Contacts contacts;

    @XmlElement(name = "ACTIONS")
    @JsonProperty("ACTIONS")
    private Actions actions;

    @XmlElement(name = "SUBMISSION_LINKS")
    @JsonProperty("SUBMISSION_LINKS")
    private SubmissionLinks submissionLinks;

    @XmlElement(name = "SUBMISSION_ATTRIBUTES")
    @JsonProperty("SUBMISSION_ATTRIBUTES")
    private SubmissionAttributes submissionAttributes;
}
