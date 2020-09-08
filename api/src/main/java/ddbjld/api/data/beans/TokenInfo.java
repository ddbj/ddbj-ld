package ddbjld.api.data.beans;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TokenInfo {
    private String accessToken;
    private String mail;
    private String uid;
    private String grantType;
    private String[] scope;
    private String realm;
    private String tokenType;
    private String expiresIn;
    private String clientId;
}
