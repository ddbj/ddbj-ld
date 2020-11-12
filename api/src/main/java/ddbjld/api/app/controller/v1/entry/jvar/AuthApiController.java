package ddbjld.api.app.controller.v1.entry.jvar;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddbjld.api.data.response.v1.entry.jvar.TokenResponse;
import ddbjld.api.data.response.v1.entry.jvar.UserResponse;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-12T15:36:38.385906+09:00[Asia/Tokyo]")
@Controller
public class AuthApiController implements AuthApi {

    private static final Logger log = LoggerFactory.getLogger(AuthApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AuthApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<UserResponse> getLoginUserInfo(@ApiParam(value = "Authorization code for OAuth2 from OpenAM",required=true) @PathVariable("code") UUID code
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<UserResponse>(objectMapper.readValue("{\n  \"uid\" : \"uid\",\n  \"mail\" : \"\",\n  \"accessToken\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"uuid\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\"\n}", UserResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<UserResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<UserResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<TokenResponse> refreshAccessToken(@ApiParam(value = "t_account.uuid",required=true) @PathVariable("accountUUID") UUID accountUUID
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<TokenResponse>(objectMapper.readValue("{\n  \"accessToken\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\"\n}", TokenResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<TokenResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<TokenResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
