/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.22).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package ddbjld.api.app.controller.v1.entry.jvar;

import ddbjld.api.data.model.v1.entry.jvar.EntriesResponse;
import ddbjld.api.data.model.v1.entry.jvar.EntryInformationResponse;
import ddbjld.api.data.model.v1.entry.jvar.EntryRequest;
import ddbjld.api.data.model.v1.entry.jvar.EntryResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-18T16:07:28.316179+09:00[Asia/Tokyo]")
@Api(value = "entry", description = "the entry API")
public interface EntryApi {

    @ApiOperation(value = "Create new entry", nickname = "createEntry", notes = "Create new entry", response = EntryResponse.class, tags={ "entry", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = EntryResponse.class),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized") })
    @RequestMapping(value = "/entry",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<EntryResponse> createEntry(@ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization
,@ApiParam(value = "Entry information" ,required=true )  @Valid @RequestBody EntryRequest body
);


    @ApiOperation(value = "Delete entry width unsubmitted status", nickname = "deleteEntry", notes = "Delete entry width unsubmitted status", tags={ "entry", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized") })
    @RequestMapping(value = "/entry/{entry_uuid}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteEntry(@ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization
,@ApiParam(value = "entry uuid",required=true) @PathVariable("entry_uuid") UUID entryUuid
);


    @ApiOperation(value = "Get entries", nickname = "getEntries", notes = "Get entries to which login user belongs", response = EntriesResponse.class, tags={ "entry", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = EntriesResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized") })
    @RequestMapping(value = "/entry",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<EntriesResponse> getEntries(@ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization
);


    @ApiOperation(value = "Get entry detail information", nickname = "getEntryInformation", notes = "Get entry detail information", response = EntryInformationResponse.class, tags={ "entry", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = EntryInformationResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/entry/{entry_uuid}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<EntryInformationResponse> getEntryInformation(@ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization
,@ApiParam(value = "entry uuid",required=true) @PathVariable("entry_uuid") UUID entryUuid
);

}

