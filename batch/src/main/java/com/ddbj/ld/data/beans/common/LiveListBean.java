package com.ddbj.ld.data.beans.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveListBean {
    private String accession;
    private String status;
    private String visibility;
    private LocalDateTime dateCreated;
    private LocalDateTime datePublished;
    private LocalDateTime dateModified;
    private String json;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
