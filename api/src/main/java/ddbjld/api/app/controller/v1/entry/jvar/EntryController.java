package ddbjld.api.app.controller.v1.entry.jvar;

import ddbjld.api.app.transact.service.AuthService;
import ddbjld.api.app.transact.service.EntryService;
import ddbjld.api.common.annotation.Auth;
import ddbjld.api.data.model.v1.entry.jvar.EntriesResponse;
import ddbjld.api.data.model.v1.entry.jvar.EntryInformationResponse;
import ddbjld.api.data.model.v1.entry.jvar.EntryRequest;
import ddbjld.api.data.model.v1.entry.jvar.EntryResponse;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
public class EntryController implements EntryApi {

    private EntryService entryService;

    private AuthService authService;

    @Override
    @Auth
    public ResponseEntity<EntryResponse> createEntry(
            @RequestHeader(value="Authorization", required=true) final String authorization,
            @Valid @RequestBody final EntryRequest body
    ) {
        var accountUUID = this.authService.getAccountUUID(authorization);

        var title       = body.getTitle();
        var description = body.getDescription();

        var response = this.entryService.createEntry(accountUUID,title, description);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var status = null == response ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;

        return new ResponseEntity<EntryResponse>(response, headers, status);
    }

    @Override
    @Auth
    public ResponseEntity<Void> deleteEntry(
            @ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) final String authorization
           ,@ApiParam(value = "entry uuid",required=true) @PathVariable("entry_uuid") final UUID entryUUID) {
        var accountUUID = this.authService.getAccountUUID(authorization);
        var status      = HttpStatus.OK;

        if(this.entryService.isDeletable(accountUUID, entryUUID)) {
            // ステータスがUnsubmitted､かつ一度も他のステータスに遷移していないときのみ、エントリーを削除する
            this.entryService.deleteEntry(entryUUID);
            log.info("Delete Entry:[{}]", entryUUID);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<Void>(null, null, status);
    }

    @Override
    @Auth
    public ResponseEntity<EntriesResponse> getEntries(
            @RequestHeader(value="Authorization", required=true) final String authorization
    ) {
        var accountUUID = this.authService.getAccountUUID(authorization);

        var response = this.entryService.getEntries(accountUUID);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var status = null == response ? HttpStatus.BAD_REQUEST : HttpStatus.OK;

        return new ResponseEntity<EntriesResponse>(response, headers, status);
    }

    @Override
    @Auth
    public ResponseEntity<EntryInformationResponse> getEntryInformation(
            @ApiParam(value = "Authorization header" ,required=true) @RequestHeader(value="Authorization", required=true) final String authorization
           ,@ApiParam(value = "entry uuid",required=true) @PathVariable("entry_uuid") final UUID entryUUID) {
        var accountUUID = this.authService.getAccountUUID(authorization);
        var status      = HttpStatus.OK;

        EntryInformationResponse response = null;

        if(this.entryService.hasRole(accountUUID, entryUUID)) {
            response = this.entryService.getEntryInformation(entryUUID);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        status = null == response ? HttpStatus.NOT_FOUND : HttpStatus.OK;

        return new ResponseEntity<EntryInformationResponse>(response, headers, status);
    }
}
