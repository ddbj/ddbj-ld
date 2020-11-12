package ddbjld.api.app.controller.v1.entry.jvar;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddbjld.api.data.response.v1.entry.jvar.EntriesResponse;
import ddbjld.api.data.response.v1.entry.jvar.EntryResponse;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-12T15:36:38.385906+09:00[Asia/Tokyo]")
@Controller
public class SubmissionApiController implements SubmissionApi {

    private static final Logger log = LoggerFactory.getLogger(SubmissionApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public SubmissionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<EntryResponse> createEntry(@ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<EntryResponse>(objectMapper.readValue("{\n  \"title\" : \"title\",\n  \"uuid\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"status\" : \"status\"\n}", EntryResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<EntryResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<EntryResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<EntriesResponse> getEntries(@ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<EntriesResponse>(objectMapper.readValue("[ {\n  \"schemas\" : {\n    \"title\" : \"title\",\n    \"uuid\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"status\" : \"status\"\n  }\n}, {\n  \"schemas\" : {\n    \"title\" : \"title\",\n    \"uuid\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"status\" : \"status\"\n  }\n} ]", EntriesResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<EntriesResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<EntriesResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
