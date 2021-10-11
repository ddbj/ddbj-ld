package com.ddbj.ld.app.controller.v1.maintenance;

import com.ddbj.ld.app.transact.dao.UserDao;
import com.ddbj.ld.common.utility.StringUtil;
import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.AuthModule;
import com.ddbj.ld.app.transact.dao.AccountDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

// FIXME
//  - 環境リセットAPI
//  - ログ出力
//  - 他と同じくSwagger化
@RestController
@RequestMapping({
        "v1/system/maintenance",
        "system/maintenance",
})
@Slf4j
@AllArgsConstructor
public class SystemMaintenanceController {

    private ConfigSet config;

    private AuthModule auth;

    private AccountDao accountDao;

    private UserDao userDao;

    @GetMapping("keygen/{phrase}")
    public ResponseEntity<UUID> keygen(@PathVariable final String phrase) {
        // 文字列からUUIDに変換
        return new ResponseEntity<UUID>(StringUtil.uuidv3(phrase), null, HttpStatus.OK);
    }

    @PostMapping("auth")
    public ResponseEntity<Boolean> authorize(final HttpServletRequest request) {
        // メンテナンス機能を使用するシークレットキー認証ヘッダの認証確認
        return new ResponseEntity<Boolean>(this.auth.requireSecretKey(request), null, HttpStatus.OK);
    }

    @PostMapping(path = "auth/curator/{uid}")
    public ResponseEntity<Void> grantCurator(
            final HttpServletRequest request,
            @PathVariable final String uid) {

        if(false == this.auth.requireSecretKey(request)) {
            log.error("Secret key is wrong.");
            return new ResponseEntity<Void>(null, null, HttpStatus.UNAUTHORIZED);
        }

        var account = this.accountDao.readByUID(uid);

        if(null == account) {
            log.error("uid does'nt exist.: {}", uid);
            return new ResponseEntity<Void>(null, null, HttpStatus.NOT_FOUND);
        }

        var user = this.userDao.readByAccountUUID(account.getUuid());
        this.userDao.updateCurator(user.getUuid(), true);

        return new ResponseEntity<Void>(null, null, HttpStatus.OK);
    }

    @DeleteMapping(path = "auth/curator/{uid}")
    public ResponseEntity<Void> deleteCurator(
            final HttpServletRequest request,
            @PathVariable final String uid) {

        if(false == this.auth.requireSecretKey(request)) {
            log.error("Secret key is wrong.");
            return new ResponseEntity<Void>(null, null, HttpStatus.UNAUTHORIZED);
        }

        var account = this.accountDao.readByUID(uid);

        if(null == account) {
            log.error("uid does'nt exist.: {}", uid);
            return new ResponseEntity<Void>(null, null, HttpStatus.NOT_FOUND);
        }

        var user = this.userDao.readByAccountUUID(account.getUuid());
        this.userDao.updateCurator(user.getUuid(), false);

        return new ResponseEntity<Void>(null, null, HttpStatus.OK);
    }
}
