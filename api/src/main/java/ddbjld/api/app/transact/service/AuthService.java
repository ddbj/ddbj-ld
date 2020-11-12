package ddbjld.api.app.transact.service;

import ddbjld.api.app.core.module.AuthModule;
import ddbjld.api.app.transact.dao.AccountDao;
import ddbjld.api.app.transact.dao.UserDao;
import ddbjld.api.data.response.v1.entry.jvar.TokenResponse;
import ddbjld.api.data.response.v1.entry.jvar.UserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * 認証機能のサービスクラス.
 *
 * @author m.tsumura
 *
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
@Slf4j
public class AuthService {

    private AuthModule module;

    private AccountDao accountDao;

    private UserDao userDao;

    /**
     * ログインしたユーザの情報を取得するメソッド.
     *
     * @param code
     *
     * @return ユーザー情報
     *
     **/
    public UserResponse getLoginUserInfo(final UUID code) {
    	// OpenAMからログイン情報を取得
        var loginInfo = this.module.getToken(code);

        if(null == loginInfo) {
            return null;
        }

        var accessToken  = loginInfo.getAccessToken();
        var refreshToken = loginInfo.getRefreshToken();

        // OpenAMからユーザーの情報を取得
        var tokenInfo = this.module.getTokenInfo(accessToken);

        if(null == tokenInfo) {
            return null;
        }

        var uid = tokenInfo.getUid();
        var account = this.accountDao.readByUID(uid);
        var accountUUID = null == account ? null : account.getUuid();

        if(null == account) {
            this.accountDao.insert(uid, refreshToken);
            account = this.accountDao.readByUID(uid);
            accountUUID = account.getUuid();
            this.userDao.insert(accountUUID);
        } else {
            this.accountDao.updateRefreshToken(accountUUID, refreshToken);
        }

        var user = this.userDao.readByAccountUUID(accountUUID);

        var response = new UserResponse();

        response.setUuid(accountUUID);
        response.setUid(uid);
        response.setAccessToken(accessToken);
        response.setMail(tokenInfo.getMail());
        response.setAdmin(user.isAdmin());

        return response;
    }

    /**
     * アクセストークンを更新するメソッド.
     *
     * @param accountUUID
     *
     * @return アクセストークン
     *
     **/
    public TokenResponse refreshAccessToken(UUID accountUUID) {
        var account = this.accountDao.read(accountUUID);
        var refreshToken = account.getRefreshToken();

        var info = this.module.getNewToken(refreshToken);

        if(null == info) {
            return null;
        }

        this.accountDao.updateRefreshToken(accountUUID, refreshToken);

        var response = new TokenResponse();
        response.setAccessToken(info.getAccessToken());

        return response;
    }
}
