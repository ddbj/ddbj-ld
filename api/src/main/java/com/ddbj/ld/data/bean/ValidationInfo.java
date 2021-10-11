package com.ddbj.ld.data.bean;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ValidationInfo {
    private UUID uuid;
    private String status;
    private LocalDateTime startTime;
}
