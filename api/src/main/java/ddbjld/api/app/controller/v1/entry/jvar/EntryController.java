package ddbjld.api.app.controller.v1.entry.jvar;

import ddbjld.api.app.core.module.FileModule;
import ddbjld.api.app.transact.service.AuthService;
import ddbjld.api.app.transact.service.EntryService;
import ddbjld.api.common.annotation.Auth;
import ddbjld.api.data.model.v1.entry.jvar.*;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    public ResponseEntity<Void> cancelRequest(
             @RequestHeader(value="Authorization", required=true) String authorization
            ,@PathVariable("entry_uuid") UUID entryUUID
            ,@PathVariable("request_uuid") UUID requestUUID
    ) {
        var accountUUID = this.authService.getAccountUUID(authorization);
        var status      = HttpStatus.OK;

        if(this.service.canCancelRequest(accountUUID, entryUUID, requestUUID)) {
            this.service.cancelRequest(entryUUID, requestUUID);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<Void>(null, null, status);
    };

    @Override
    @Auth
    public ResponseEntity<Void> checkUpdateToken(@RequestHeader(value="Authorization", required=true) String authorization
            ,@PathVariable("entry_uuid") UUID entryUUID
            ,@PathVariable("update_token") UUID updateToken
    ) {
        var accountUUID = this.authService.getAccountUUID(authorization);
        var status      = HttpStatus.OK;

        if(this.service.canUpdate(accountUUID, entryUUID, updateToken)) {
            // 何もしない
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<Void>(null, null, status);
    };

    @Override
    @Auth
    public ResponseEntity<EntryResponse> createEntry(
            @RequestHeader(value="Authorization", required=true) final String authorization,
            @Valid @RequestBody final EntryRequest body
    ) {
        var accountUUID = this.authService.getAccountUUID(authorization);

        var response = this.service.createEntry(accountUUID, body.getType());

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var status = null == response ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;

        return new ResponseEntity<EntryResponse>(response, headers, status);
    }

    @Override
    @Auth
    public ResponseEntity<RequestResponse> createRequest(
             @RequestHeader(value="Authorization", required=true) String authorization
            ,@PathVariable("entry_uuid") UUID entryUUID
            ,@Valid @RequestBody RequestRequest body
    ) {
        var accountUUID = this.authService.getAccountUUID(authorization);
        var type = body.getType();
        RequestResponse response = null;

        if(this.service.canRequest(accountUUID, entryUUID, type)) {
            response = this.service.createRequest(accountUUID, entryUUID, body.getType(), body.getComment());
        }

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var status = null == response ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;

        return new ResponseEntity<RequestResponse>(response, headers, status);
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
            this.service.deleteComment(entryUUID, commentUUID);
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

    // アップロードされたファイルを削除する、一律で論理削除
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
    public ResponseEntity<Resource> downloadFile(
             @RequestHeader(value="Authorization", required=true) String authorization
            ,@PathVariable("entry_uuid") UUID entryUUID
            ,@PathVariable("file_type") String fileType
            ,@PathVariable("file_name") String fileName
    ) {
        var accountUUID   = this.authService.getAccountUUID(authorization);
        var status        = HttpStatus.OK;
        Resource response = null;

        if(this.service.hasRole(accountUUID, entryUUID)) {
            response = this.service.downloadFile(entryUUID, fileType, fileName);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        if(null == response) {
            status = HttpStatus.NOT_FOUND;
        }

        // 強制ダウンロードを示すヘッダをレスポンスに付与
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/force-download");
        headers.add("Content-Disposition", "attachment; filename*=utf-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        return new ResponseEntity<Resource>(response, headers, status);
    }

    @Override
    @Auth
    public ResponseEntity<EntriesResponse> getEntries(
            @RequestHeader(value="Authorization", required=true) final String authorization
    ) {
        var accountUUID = this.authService.getAccountUUID(authorization);
        var isCurator     = this.authService.isCurator(accountUUID);

        var response = isCurator
                ? this.service.getAllEntries(accountUUID)
                : this.service.getEntries(accountUUID);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var status = null == response ? HttpStatus.BAD_REQUEST : HttpStatus.OK;

        return new ResponseEntity<EntriesResponse>(response, headers, status);
    }

    @Override
    @Auth
    public ResponseEntity<EntryInfoResponse> getEntryInfo(
            @RequestHeader(value="Authorization", required=true) final String authorization
           ,@PathVariable("entry_uuid") final UUID entryUUID) {

        var accountUUID = this.authService.getAccountUUID(authorization);
        HttpStatus status = HttpStatus.OK;

        EntryInfoResponse response = null;

        if(this.service.hasRole(accountUUID, entryUUID)) {
            response = this.service.getEntryInfo(accountUUID, entryUUID);

            if(null == response) {
                status = HttpStatus.NOT_FOUND;
            }
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<EntryInfoResponse>(response, headers, status);
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
            var curator   = body.isCurator();
            response = this.service.createComment(entryUUID, accountUUID, comment, curator);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        if(null == response) {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<CommentResponse>(response, null, status);
    }

    @Override
    @Auth
    public ResponseEntity<Void> submitEntry(
             @RequestHeader(value="Authorization", required=true) String authorization
            ,@ApiParam(value = "entry uuid",required=true) @PathVariable("entry_uuid") UUID entryUUID
    ) {
        var accountUUID          = this.authService.getAccountUUID(authorization);
        var status               = HttpStatus.OK;

        if(this.service.isSubmittable(accountUUID, entryUUID)) {
            this.service.submitEntry(entryUUID);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<Void>(null, null, status);
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
            var curator = body.isCurator();
            response    = this.service.editComment(accountUUID, entryUUID, commentUUID, comment, curator);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<CommentResponse>(response, null, status);
    }

    @Override
    @Auth
    public ResponseEntity<RequestResponse> editRequest(
            @RequestHeader(value="Authorization", required=true) String authorization
            ,@PathVariable("entry_uuid") UUID entryUUID
            ,@PathVariable("request_uuid") UUID requestUUID
            ,@Valid @RequestBody RequestRequest body
    ) {
        var accountUUID = this.authService.getAccountUUID(authorization);
        var status      = HttpStatus.OK;
        RequestResponse response = null;

        if(this.service.canCancelRequest(accountUUID, entryUUID, requestUUID)) {
            response = this.service.editRequest(accountUUID, entryUUID, requestUUID, body.getComment());
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<RequestResponse>(response, null, status);
    };

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
        if(this.fileModule.validateFileType(multipartFile, fileType, fileName)) {
            // 何もしない
        } else {
            // FIXME 理由も入れられるようにする
            return new ResponseEntity<Void>(null, null, HttpStatus.BAD_REQUEST);
        }

        // 既に他の名前のExcelファイルの重複していないかチェック
        if(this.service.validateDuplicateWorkBook(entryUUID, fileType, fileName)) {
            // 何もしない
        } else {
            // FIXME 理由も入れられるようにする
            return new ResponseEntity<Void>(null, null, HttpStatus.BAD_REQUEST);
        }

        if(this.service.validateUpdateToken(uploadToken)) {
            this.service.uploadFile(entryUUID, fileType, fileName, uploadToken, multipartFile);
        } else {
            // FIXME 理由も入れられるようにする
            return new ResponseEntity<Void>(null, null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Void>(null, null, HttpStatus.OK);
    }

    @Override
    @Auth
    public ResponseEntity<ValidationResponse> validateMetadata(@RequestHeader(value="Authorization", required=true) String authorization
            ,@PathVariable("entry_uuid") UUID entryUUID
    ) {
        var accountUUID             = this.authService.getAccountUUID(authorization);
        var status                  = HttpStatus.OK;
        var response                = new ValidationResponse();

        if(this.service.hasRole(accountUUID, entryUUID)) {
            if(this.service.canValidate(entryUUID)) {
                response = this.service.validateMetadata(entryUUID);
            } else {
                status = HttpStatus.BAD_REQUEST;
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
        }

        if(null != response.getErrorMessage()) {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<ValidationResponse>(response, null, status);
    };
}
