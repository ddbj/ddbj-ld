package com.ddbj.ld.data.beans.sra.common;

import com.fasterxml.jackson.annotation.*;

public class SequencingDirectives {
    private String alignmentIncludesUnalignedReads;
    private String alignmentMarksDuplicateReads;
    private String alignmentIncludesFailedReads;

    @JsonProperty("alignment_includes_unaligned_reads")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAlignmentIncludesUnalignedReads() { return alignmentIncludesUnalignedReads; }
    @JsonProperty("alignment_includes_unaligned_reads")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAlignmentIncludesUnalignedReads(String value) { this.alignmentIncludesUnalignedReads = value; }

    @JsonProperty("alignment_marks_duplicate_reads")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAlignmentMarksDuplicateReads() { return alignmentMarksDuplicateReads; }
    @JsonProperty("alignment_marks_duplicate_reads")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAlignmentMarksDuplicateReads(String value) { this.alignmentMarksDuplicateReads = value; }

    @JsonProperty("alignment_includes_failed_reads")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAlignmentIncludesFailedReads() { return alignmentIncludesFailedReads; }
    @JsonProperty("alignment_includes_failed_reads")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAlignmentIncludesFailedReads(String value) { this.alignmentIncludesFailedReads = value; }
}
