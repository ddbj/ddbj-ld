package com.ddbj.ld.data.beans.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BioDateBean {
    private String accession;
    private LocalDateTime dateCreated;
    private LocalDateTime datePublished;
    private LocalDateTime dateModified;
}
