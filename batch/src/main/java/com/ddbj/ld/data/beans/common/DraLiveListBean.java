package com.ddbj.ld.data.beans.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DraLiveListBean {
    private String accession;
    private String submission;
    private String visibility;
    private LocalDateTime updated;
    private String type;
    private String center;
    private String alias;
    private String md5sum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
