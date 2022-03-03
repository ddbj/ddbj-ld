package com.ddbj.ld.data.beans.bioproject;

import com.ddbj.ld.data.beans.common.LocalDateAdapter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Submission {
    @XmlAttribute(name="submitted")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonProperty("submitted")
    private LocalDate submitted;

    @XmlAttribute(name="last_update")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonProperty("last_update")
    private LocalDate lastUpdate;

    @XmlAttribute(name="status")
    @JsonProperty("status")
    private String status;

    @XmlAttribute(name="related_to")
    @JsonProperty("related_to")
    private String relatedTo;

    @XmlAttribute(name="submission_id")
    @JsonProperty("submission_id")
    private String submissionID;

    @XmlElement(name="Description")
    @JsonProperty("Description")
    private Description description;

    @XmlElement(name="Context")
    @JsonProperty("Context")
    private Context context;

    @XmlElement(name="Action")
    @JsonProperty("Action")
    private Action action;
}
