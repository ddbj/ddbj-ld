package ddbjld.api.data.beans;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ValidationInfo {
    private UUID uuid;
    private String status;
    private LocalDateTime startTime;
}
