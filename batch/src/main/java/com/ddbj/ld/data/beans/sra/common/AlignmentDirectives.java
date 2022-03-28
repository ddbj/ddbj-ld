package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AlignmentDirectives {
    @XmlAttribute(name = "alignment_includes_unaligned_reads")
    @JsonProperty("alignment_includes_unaligned_reads")
    private String alignmentIncludesUnalignedReads;

    @XmlAttribute(name = "alignment_marks_duplicate_reads")
    @JsonProperty("alignment_marks_duplicate_reads")
    private String alignmentMarksDuplicateReads;

    @XmlAttribute(name = "alignment_includes_failed_reads")
    @JsonProperty("alignment_includes_failed_reads")
    private String alignmentIncludesFailedReads;
}
