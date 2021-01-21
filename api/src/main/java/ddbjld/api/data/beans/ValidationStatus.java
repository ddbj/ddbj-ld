package ddbjld.api.data.beans;

import lombok.Data;

import java.util.UUID;

@Data
public class ValidationStatus {
    private UUID uuid;
    private String status;
    private String startTime;
    private String endTime;
}
