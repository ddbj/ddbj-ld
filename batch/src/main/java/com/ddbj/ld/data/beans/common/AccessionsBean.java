package com.ddbj.ld.data.beans.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessionsBean {
    private String accession;
    private String submission;
    private String status;
    private LocalDateTime updated;
    private LocalDateTime published;
    private LocalDateTime received;
    private String type;
    private String center;
    private String visibility;
    private String alias;
    private String experiment;
    private String sample;
    private String study;
    private byte loaded;
    private String spots;
    private String bases;
    private String md5sum;
    private String bioSample;
    private String bioProject;
    private String rePlacedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
