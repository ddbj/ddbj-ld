/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.22).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package ddbjld.api.app.controller.v1.entry.jvar;

import ddbjld.api.data.model.v1.entry.jvar.*;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-25T22:22:20.692643+09:00[Asia/Tokyo]")
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


    @ApiOperation(value = "Delete a comment", nickname = "deleteComment", notes = "Delete a comment", tags={ "comment", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized") })
    @RequestMapping(value = "/entry/{entry_uuid}/comment/{comment_uuid}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteComment(@ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization
            ,@ApiParam(value = "entry uuid",required=true) @PathVariable("entry_uuid") UUID entryUuid
            ,@ApiParam(value = "comment uuid",required=true) @PathVariable("comment_uuid") UUID commentUuid
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


    @ApiOperation(value = "Delete file", nickname = "deleteFile", notes = "Delete a file from a entry", tags={ "file", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 409, message = "Conflicted") })
    @RequestMapping(value = "/entry/{entry_uuid}/file/{file_type}/{file_name}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteFile(@ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization
            ,@ApiParam(value = "entry uuid",required=true) @PathVariable("entry_uuid") UUID entryUuid
            ,@ApiParam(value = "file type",required=true) @PathVariable("file_type") String fileType
            ,@ApiParam(value = "file name",required=true) @PathVariable("file_name") String fileName
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


    @ApiOperation(value = "get upload token", nickname = "getUploadToken", notes = "get token for upload", response = UploadTokenResponse.class, tags={ "file", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = UploadTokenResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 409, message = "Conflicted") })
    @RequestMapping(value = "/entry/{entry_uuid}/file/{file_type}/{file_name}/pre_upload",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<UploadTokenResponse> getUploadToken(@ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization
            ,@ApiParam(value = "entry uuid",required=true) @PathVariable("entry_uuid") UUID entryUuid
            ,@ApiParam(value = "file type",required=true) @PathVariable("file_type") String fileType
            ,@ApiParam(value = "file name",required=true) @PathVariable("file_name") String fileName
    );


    @ApiOperation(value = "Post a new comment", nickname = "postComment", notes = "Post a new comment", response = CommentResponse.class, tags={ "comment", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = CommentResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized") })
    @RequestMapping(value = "/entry/{entry_uuid}/comment",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<CommentResponse> postComment(@ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization
            ,@ApiParam(value = "entry uuid",required=true) @PathVariable("entry_uuid") UUID entryUuid
            ,@ApiParam(value = "Comment information" ,required=true )  @Valid @RequestBody CommentRequest body
    );


    @ApiOperation(value = "Update a comment", nickname = "updateComment", notes = "Update a comment", response = CommentResponse.class, tags={ "comment", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = CommentResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized") })
    @RequestMapping(value = "/entry/{entry_uuid}/comment/{comment_uuid}",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<CommentResponse> updateComment(@ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization
            ,@ApiParam(value = "entry uuid",required=true) @PathVariable("entry_uuid") UUID entryUuid
            ,@ApiParam(value = "comment uuid",required=true) @PathVariable("comment_uuid") UUID commentUuid
            ,@ApiParam(value = "Comment information" ,required=true )  @Valid @RequestBody CommentRequest body
    );


    @ApiOperation(value = "Upload file", nickname = "uploadFile", notes = "Upload file to a entity", tags={ "file", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 401, message = "Unauthorized") })
    @RequestMapping(value = "/entry/{entry_uuid}/file/{file_type}/{file_name}/{upload_token}/upload",
            method = RequestMethod.POST)
    ResponseEntity<Void> uploadFile(@ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization
            ,@ApiParam(value = "entry uuid",required=true) @PathVariable("entry_uuid") UUID entryUuid
            ,@ApiParam(value = "file type",required=true) @PathVariable("file_type") String fileType
            ,@ApiParam(value = "file name",required=true) @PathVariable("file_name") String fileName
            ,@ApiParam(value = "upload token",required=true) @PathVariable("upload_token") UUID uploadToken
    );

}

