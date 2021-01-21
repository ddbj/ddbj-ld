package ddbjld.api.data.beans;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class ValidationResult {
    private UUID uuid;
    private String status;
    private String startTime;
    private String endTime;
    private Map<String, Object> result;
}
