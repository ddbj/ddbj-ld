package com.ddbj.ld.data.beans.sra.experiment;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Pool {
    @XmlElement(name = "DEFAULT_MEMBER")
    @JsonProperty("DEFAULT_MEMBER")
    private Member defaultMember;

    @XmlElement(name = "MEMBER")
    @JsonProperty("MEMBER")
    @JsonDeserialize(using = ExperimentDeserializers.MemberListDeserializer.class)
    private List<Member> member;
}
