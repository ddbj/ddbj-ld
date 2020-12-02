package ddbjld.api.app.controller.v1.entry.jvar;

import ddbjld.api.app.core.module.FileModule;
import ddbjld.api.app.transact.service.AuthService;
import ddbjld.api.app.transact.service.EntryService;
import ddbjld.api.common.annotation.Auth;
import ddbjld.api.data.model.v1.entry.jvar.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
public class EntryController implements EntryApi {

    private EntryService service;

    private AuthService authService;

    private FileModule fileModule;

    @Override
    @Auth
    public ResponseEntity<EntryResponse> createEntry(
            @RequestHeader(value="Authorization", required=true) final String authorization,
            @Valid @RequestBody final EntryRequest body
    ) {
        var accountUUID = this.authService.getAccountUUID(authorization);

        var title       = body.getTitle();
        var description = body.getDescription();

        var response = this.service.createEntry(accountUUID,title, description);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var status = null == response ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;

        return new ResponseEntity<EntryResponse>(response, headers, status);
    }

    @Override
    @Auth
    public ResponseEntity<Void> deleteComment(
             @RequestHeader(value="Authorization", required=true) String authorization
            ,@PathVariable("entry_uuid") UUID entryUUID
            ,@PathVariable("comment_uuid") UUID commentUUID
    ) {
        var accountUUID = this.authService.getAccountUUID(authorization);
        var status      = HttpStatus.OK;

        if(this.service.canEditComment(accountUUID, commentUUID)) {
            this.service.deleteComment(commentUUID);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<Void>(null, null, status);
    }

    @Override
    @Auth
    public ResponseEntity<Void> deleteEntry(
            @RequestHeader(value="Authorization", required=true) final String authorization
           ,@PathVariable("entry_uuid") final UUID entryUUID) {
        var accountUUID = this.authService.getAccountUUID(authorization);
        var status      = HttpStatus.OK;

        if(this.service.isDeletable(accountUUID, entryUUID)) {
            // ステータスがUnsubmitted､かつ一度も他のステータスに遷移していないときのみ、エントリーを削除する
            this.service.deleteEntry(entryUUID);
            log.info("Delete Entry:[{}]", entryUUID);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<Void>(null, null, status);
    }

    // FIXME
    //  - ファイルの削除方法と削除できる条件
    //  - 論理削除？物理削除？
    //  - Statusが一度も別のステータスに遷移していないUnsubmittedだったら物理でいいかもしれないが…
    @Override
    @Auth
    public ResponseEntity<Void> deleteFile(
             @RequestHeader(value="Authorization", required=true) String authorization
            ,@PathVariable("entry_uuid") final UUID entryUUID
            ,@PathVariable("file_type") final String fileType
            ,@PathVariable("file_name") final String fileName
    ) {
        var accountUUID = this.authService.getAccountUUID(authorization);
        var status      = HttpStatus.OK;

        if(this.service.hasRole(accountUUID, entryUUID)) {
            if(this.service.canUpload(entryUUID, fileType, fileName)) {
                this.service.deleteFile(entryUUID, fileType, fileName);
            } else {
                status = HttpStatus.CONFLICT;
            }
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
        var isAdmin     = this.authService.isAdmin(accountUUID);

        var response = isAdmin
                ? this.service.getAllEntries(accountUUID)
                : this.service.getEntries(accountUUID);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var status = null == response ? HttpStatus.BAD_REQUEST : HttpStatus.OK;

        return new ResponseEntity<EntriesResponse>(response, headers, status);
    }

    @Override
    @Auth
    public ResponseEntity<EntryInformationResponse> getEntryInformation(
            @RequestHeader(value="Authorization", required=true) final String authorization
           ,@PathVariable("entry_uuid") final UUID entryUUID) {

        var accountUUID = this.authService.getAccountUUID(authorization);
        var status      = HttpStatus.OK;

        EntryInformationResponse response = null;

        if(this.service.hasRole(accountUUID, entryUUID)) {
            response = this.service.getEntryInformation(accountUUID, entryUUID);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        status = null == response ? HttpStatus.NOT_FOUND : HttpStatus.OK;

        return new ResponseEntity<EntryInformationResponse>(response, headers, status);
    }

    @Override
    @Auth
    public ResponseEntity<UploadTokenResponse> getUploadToken(
            @RequestHeader(value="Authorization", required=true) String authorization
            ,@PathVariable("entry_uuid") UUID entryUUID
            ,@PathVariable("file_type") String fileType
            ,@PathVariable("file_name") String fileName
    ) {
        var accountUUID = this.authService.getAccountUUID(authorization);

        if(this.service.hasRole(accountUUID, entryUUID)) {
            // 何もしない
        } else {
            // FIXME 理由も入れられるようにする
            return new ResponseEntity<UploadTokenResponse>(null, null, HttpStatus.BAD_REQUEST);
        }

        if(this.fileModule.validateFileType(fileType, fileName)) {
            // 何もしない
        } else {
            // FIXME 理由も入れられるようにする
            return new ResponseEntity<UploadTokenResponse>(null, null, HttpStatus.BAD_REQUEST);
        }

        if(this.service.canUpload(entryUUID, fileType, fileName)) {
            // 何もしない
        } else {
            // FIXME 理由も入れられるようにする
            return new ResponseEntity<UploadTokenResponse>(null, null, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<UploadTokenResponse>(
                this.service.getUploadToken(entryUUID, fileType, fileName),
                null,
                HttpStatus.OK
        );
    }

    @Override
    @Auth
    public ResponseEntity<CommentResponse> postComment(
            @RequestHeader(value="Authorization", required=true) String authorization
            ,@PathVariable("entry_uuid") UUID entryUUID
            ,@Valid @RequestBody CommentRequest body
    ) {
        var accountUUID          = this.authService.getAccountUUID(authorization);
        var status               = HttpStatus.CREATED;
        CommentResponse response = null;

        if(this.service.hasRole(accountUUID, entryUUID)) {
            var comment = body.getComment();
            response = this.service.createComment(entryUUID, accountUUID, comment);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<CommentResponse>(response, null, status);
    }

    @Override
    @Auth
    public ResponseEntity<CommentResponse> editComment(
            @RequestHeader(value="Authorization", required=true) String authorization
            ,@PathVariable("entry_uuid") UUID entryUUID
            ,@PathVariable("comment_uuid") UUID commentUUID
            ,@Valid @RequestBody CommentRequest body
    ) {
        var accountUUID          = this.authService.getAccountUUID(authorization);
        var status               = HttpStatus.OK;
        CommentResponse response = null;

        if(this.service.canEditComment(accountUUID, commentUUID)) {
            var comment = body.getComment();
            response = this.service.editComment(accountUUID, commentUUID, comment);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<CommentResponse>(response, null, status);
    }

    @Override
    public ResponseEntity<Void> uploadFile(
             @PathVariable("entry_uuid") UUID entryUUID
            ,@PathVariable("file_type") String fileType
            ,@PathVariable("file_name") String fileName
            ,@PathVariable("upload_token") UUID uploadToken
             // FIXME
             //  - 自動生成のコード(EntryApi.java)にはなかったが手動で追加した
             //  - MultipartFile[]を自動生成できる方法が見つかったら、そちらで行いたい
             //  - https://github.com/OpenAPITools/openapi-generator/issues/4803
            ,@RequestParam("file") MultipartFile multipartFile
    ) {
        if(this.fileModule.validateFileType(fileType, fileName)) {
            // 何もしない
        } else {
            // FIXME 理由も入れられるようにする
            return new ResponseEntity<Void>(null, null, HttpStatus.BAD_REQUEST);
        }

        if(this.service.validateUpdateToken(uploadToken)) {
            this.service.uploadFile(entryUUID, fileType, fileName, uploadToken, multipartFile);
        } else {
            return new ResponseEntity<Void>(null, null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Void>(null, null, HttpStatus.OK);
    }
}
