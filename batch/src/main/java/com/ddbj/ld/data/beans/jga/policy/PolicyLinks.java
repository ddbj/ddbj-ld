package com.ddbj.ld.data.beans.jga.policy;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PolicyLinks {
    @XmlElement(name = "POLICY_LINK")
    @JsonProperty("POLICY_LINK")
    private PolicyLink policyLink;
}
