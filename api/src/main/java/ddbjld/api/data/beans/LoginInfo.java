package ddbjld.api.data.beans;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.UUID;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginInfo {
    private UUID accessToken;
    private UUID refreshToken;
    private String scope;
    private String tokenType;
    private String expiresIn;
}
