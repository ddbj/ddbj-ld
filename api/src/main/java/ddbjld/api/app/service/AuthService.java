package ddbjld.api.app.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import ddbjld.api.app.core.module.AuthModule;
import ddbjld.api.app.service.dao.DraftMetadataDao;
import ddbjld.api.app.service.dao.ProjectDao;
import ddbjld.api.app.service.dao.ProjectDao.TProjectDataReader;
import ddbjld.api.data.beans.*;
import ddbjld.api.data.json.AggregateJson;
import ddbjld.api.data.json.ListdataJson;
import ddbjld.api.data.json.MetadataJson;
import ddbjld.api.data.values.DraftMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.app.service.dao.AccountDao;
import ddbjld.api.app.service.dao.AccountDao.TAccountDataReader;
import ddbjld.api.app.service.dao.ProjectRoleDao;
import ddbjld.api.app.service.dao.ProjectRoleDao.TProjectRoleDataReader;
import ddbjld.api.app.service.dao.UserDao;
import ddbjld.api.app.service.dao.UserDao.TUserDataReader;
import ddbjld.api.common.exceptions.RestApiException;
import ddbjld.api.common.utility.JsonMapper;

/**
 * 認証機能のサービスクラス.
 *
 * @author m.tsumura
 *
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AuthService {

    @Autowired
    ConfigSet config;

    @Autowired
    private RestTemplate restTemplate; //TODO：後でRestClient部品に差し替え。

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectRoleDao projectRoleDao;

    @Autowired
    private DraftMetadataDao draftMetadataDao;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private AuthModule authModule;

    // FIXME Serviceの横呼び出しはアレ
    @Autowired
    private FileService fileService;

    /**
     * アクセストークンの有効性を判定するメソッド.
     *
     * <p>
     * OpenAMのtokeninfo APIを用い、アクセストークンを判定する。<br>
     * トランザクションは発生しない処理だが、Springに登録されたrestTemplateを使用するため、Serviceクラスに配置<br>
     * </p>
     *
     * @param accessToken
     *
     * @return 判定結果
     *
     **/
    public boolean isAuth(final String accessToken) {
        if(accessToken.isEmpty()) {
            return false;
        }

        var endpoints = config.openam.endpoints;
        final String url = endpoints.TOKEN_INFO + accessToken;

        try {
            restTemplate.getForObject(url, TokenInfo.class);
        } catch (RestClientException e) {
            // 401が返ったらExceptionが返る
            return false;
        }

        return true;
    }

    /**
     * ログインしたユーザの情報を取得するメソッド.
     *
     * @param code
     *
     * @return ユーザー情報
     *
     **/
    public UserInfo getLoginUserInfo(final String code) {
    	// OpenAMからログイン情報を取得
        var loginInfo = this.authModule.getToken(code);

        if(null == loginInfo) {
            // 認証失敗、401を返す
            throw new RestApiException(HttpStatus.UNAUTHORIZED);
        }

        var accessToken  = loginInfo.getAccessToken();
        var refreshToken = loginInfo.getRefreshToken();

        // OpenAMからトークンを取得
        var tokenInfo = this.authModule.getTokenInfo(accessToken);

        if(null == tokenInfo) {
            throw new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        var uid  = tokenInfo.getUid();
        var mail = tokenInfo.getMail();

        // ddbj-db にアカウント情報を連携
        if(accountDao.existsByUid(uid)) {
            accountDao.updateRefreshTokenByUid(uid, refreshToken);
        } else {
            var uuid = UUID.randomUUID();
            accountDao.create(uuid, uid, refreshToken);
            // 文字列からオブジェクトにするときに、キャメルケースでないと不可なため、display_nameとする
            userDao.create(UUID.randomUUID(), uuid, "{\"display_name\": \"\", \"lang\": \"\"}", false);
        }
		
        // アカウント情報を取得
		var account = new TAccountDataReader( accountDao.readByUid( uid ) );

		if (null == account.data) {
            throw new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR);
		}

        var accountUuid = account.uuid();
        var user = new TUserDataReader( userDao.readByAccountUuid(accountUuid) );

        if(null == user.data) {
            throw new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        boolean admin               = user.admin();
        ProfileInfo profile = JsonMapper.parse(user.profileJson(), ProfileInfo.class);

        List<Map<String, Object>> projectRoleList = projectRoleDao.readByAccountUuid(accountUuid);
        List<RoleInfo>  role                      = new ArrayList<>();
        List<ProjectViewData> project             = new ArrayList<>();
        var publishedDateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        projectRoleList.stream().forEach((pr) -> {
            var projectRole = new TProjectRoleDataReader( pr );
            var inf = new RoleInfo();

            var projectUuid = projectRole.projectUUID();
            var ids         = this.projectDao.ids(projectUuid);
            var projectId   = ids.id;
            var owner       = projectRole.readable();
            var writable    = projectRole.writable();
            var readable    = projectRole.writable();

            inf.setProjectId(projectId);
            inf.setOwner(owner);
            inf.setWritable(writable);
            inf.setReadable(readable);

            // FIXME 型を変更
            inf.setExpireDate(projectRole.expireDate());

            role.add(inf);

            // FIXME 切り出したい
            var prjReader = new ProjectDao.TProjectDataReader(this.projectDao.read(projectId));
            var metadata = MetadataJson.parse(prjReader.metadataJson());

            var published = prjReader.published();
            var hidden    = prjReader.hidden();

            var publicFileList = false == published || hidden
                    ? null
                    : fileService.getProjectFile(projectId);

            var aggregate = AggregateJson.parse(prjReader.aggregateJson());
            var listdata = ListdataJson.parse(prjReader.listdataJson());
            var publishedDate = prjReader.firstPublishedString(publishedDateFormat);
            var editing = prjReader.editing();

            var draftReader =
                    editing
                        ? new DraftMetadataDao.TDraftMetadataDataReader(this.draftMetadataDao.read(projectUuid))
                        : null;

            var draftMetadataJson =
                    null == draftReader || draftReader.data == null
                        ? null
                        : MetadataJson.parse(draftReader.metadataJson());

            var draftSheets =
                    null == draftMetadataJson
                        ? null
                        : draftMetadataJson.sheets;

            var draftToken =
                    null == draftReader || draftReader.data == null
                        ? null
                        : draftReader.dataToken();

            var draftMetadata = new DraftMetadata(draftSheets, draftToken);

            var draftFileList = readable && editing
                ? fileService.getProjectFile(accountUuid, projectId)
                : null;

            var prj = new ProjectViewData(
                    ids,
                    metadata,
                    publicFileList,
                    aggregate,
                    listdata,
                    draftMetadata,
                    draftFileList,
                    publishedDate,
                    editing,
                    hidden
            );

            project.add(prj);
        });

        var sortedProject = project.stream().sorted(new Comparator<ProjectViewData>(){

            @Override
            public int compare(ProjectViewData o1, ProjectViewData o2) {
                return o1.getIds().id.compareTo(o2.getIds().id);
            }

        }).collect(Collectors.toList());

        UserInfo userInfo = new UserInfo();
        userInfo.setAccessToken(accessToken);
        userInfo.setUid(uid);
        userInfo.setMail(mail);
        userInfo.setAccountUuid(accountUuid);
        userInfo.setAdmin(admin);
        userInfo.setProfile(profile);
        userInfo.setRole(role);
        userInfo.setProject(sortedProject);

        return userInfo;
    }

    /**
     * アカウントのアクセストークンを再発行するメソッド.
     *
     * <p>
     * アカウントテーブルを検索し、リフレッシュトークンを取得する。<br>
     * リフレッシュトークンでOpenAMにアクセストークン再発行依頼をする。<br>
     * リフレッシュトークンも再発行されるのでアカウントテーブルのリフレッシュトークンを更新する。
     * </p>
     *
     * @param accountUUID
     *
     * @return アクセストークン
     *
     **/
    public AccessTokenInfo updateAccessToken( final UUID accountUUID ) {
        var account      = new TAccountDataReader( accountDao.read( accountUUID ) );
        var refreshToken = account.refreshToken();

        var loginInfo    = this.authModule.getNewToken(refreshToken);

        if(null == loginInfo) {
            throw new RestApiException(HttpStatus.UNAUTHORIZED);
        }

        var newRefreshToken = loginInfo.getRefreshToken();

        // 新しいリフレッシュトークンをDBに登録
        this.accountDao.updateRefreshToken(accountUUID, newRefreshToken);

        var accessToken                 = loginInfo.getAccessToken();
        AccessTokenInfo accessTokenInfo = new AccessTokenInfo();
        accessTokenInfo.setAccessToken(accessToken);

        return accessTokenInfo;
    }

    /**
     * プロジェクトに招待するユーザーの一覧を取得するメソッド.
     *
     * <p>
     * OpenAMのAPI「/json/realms/root/users?_queryid=」を用い、ユーザーの一覧を取得する。<br>
     * t_account, t_project_roleを検索し、招待できるユーザーのみを返却する。
     * </p>
     *
     * @param authAccountUUID
     * @param projectId
     *
     * @return 招待対象一覧
     *
     **/
    public List<InvitationInfo> getInvitationList(final UUID authAccountUUID, final String projectId) {
        // 処理をリクエストしたアカウントが所有者か編集者でないと取得させない
        var project     = new TProjectDataReader(projectDao.read(projectId));
        var projectUuid = project.uuid();

        if(projectRoleDao.hasOwner(authAccountUUID, projectUuid)
        || projectRoleDao.hasWritable(authAccountUUID, projectUuid)) {
            // 何もしない
        } else {
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        // 管理者ユーザでOpenAMにログイン
        var tokenId = this.authModule.loginAdmin();

        if (null == tokenId) {
            throw new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // OpenAMにアクセスしユーザー情報を取得
        var amUserInfoList = this.authModule.getAmUserList(tokenId);

        if (null == amUserInfoList) {
            throw new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Collections.sort(amUserInfoList, new Comparator<AmUserInfo>(){
            public int compare(AmUserInfo a1, AmUserInfo a2) {
                return a1.get_id().compareTo(a2.get_id());
            }
        });

        List<InvitationInfo> invitationInfoList = new ArrayList<>();

        for(AmUserInfo amUserInfo : amUserInfoList) {
            var uidArr   = amUserInfo.getUid();
            var mailArr  = amUserInfo.getMail();

            if(null == uidArr) {
                // amAdminなどOpenAMの管理者ユーザはuidを持っていないため、除外
                continue;
            }

            if(null == mailArr) {
                // mailをもっていないユーザは招待できないため、除外
                continue;
            }

            var uid  = uidArr[0];
            var mail = mailArr[0];

            // t_accountとt_project_roleを検索しユーザーがいなかったらInvitationInfoに追加
            var result = accountDao.readByUid(uid);

            if(null == result) {
                var info = new InvitationInfo();
                info.setUid(uid);
                info.setMail(mail);

                invitationInfoList.add(info);

                continue;
            }

            var account     = new TAccountDataReader(result);
            var targetUuid  = account.uuid();
            var projectRole = projectRoleDao.read(targetUuid, projectUuid);

            if(null == projectRole) {
                var info = new InvitationInfo();
                info.setUid(uid);
                info.setMail(mail);

                invitationInfoList.add(info);
            }
        }

        return invitationInfoList;
    }

    /**
     * 既にDDBJのアカウント管理システムで登録されているアカウントを招待するメソッド.
     *
     * <p>
     * t_account, t_project_roleを検索し、招待できるアカウントのみを招待する。<br>
     * 招待されたアカウントには招待メールが送信される。
     * </p>
     *
     * @param accountUuid
     * @param projectId
     * @param targets
     *
     * @return void
     *
     **/
    public void inviteProject(
            final UUID accountUuid,
            final String projectId,
            final List<InvitationTargetInfo> targets) {
        // 処理をリクエストしたアカウントが所有者か編集者でないと取得させない
        var project     = new TProjectDataReader(projectDao.read(projectId));
        var projectUuid = project.uuid();

        if(projectRoleDao.hasOwner(accountUuid, projectUuid)
        || projectRoleDao.hasWritable(accountUuid, projectUuid)) {
            // 何もしない
        } else {
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        for(InvitationTargetInfo target: targets) {
            var uid        = target.getUid();
            var expireDate = target.getExpireDate();

            var tokenId  = this.authModule.loginAdmin();
            var userInfo = this.authModule.getAmUser(tokenId, uid);

            if(null == userInfo) {
                log.warn("[SKIP]" + uid +"はOpenAMに登録されていません");
                continue;
            }

            var account = new TAccountDataReader(this.accountDao.readByUid(uid));

            if(null == account.data) {
                // アカウントの情報がDDBJになければ作成する
                this.accountDao.create(uid, null);
                account = new TAccountDataReader(this.accountDao.readByUid(uid));

                this.userDao.create(account.uuid());
            }

            var targetUuid = account.uuid();

            // 登録・更新する対象のアカウントがプロジェクトの所有者であったら更新させない
            if(this.projectRoleDao.hasOwner(targetUuid, projectUuid)) {
                log.debug("[SKIP]" + uid +"はプロジェクト登録者です");

                throw new RestApiException(HttpStatus.BAD_REQUEST);
            }

            // 登録・更新する対象のアカウントがプロジェクトの編集者であったら更新させない
            if(this.projectRoleDao.hasWritable(targetUuid, projectUuid)) {
                log.debug("[SKIP]" + uid +"はプロジェクト編集者です");

                throw new RestApiException(HttpStatus.BAD_REQUEST);
            }

            if(this.projectRoleDao.hasReadable(targetUuid, projectUuid)) {
                this.projectRoleDao.updateEditor(projectUuid, targetUuid, expireDate);
            } else {
                this.projectRoleDao.createEditor(projectUuid, targetUuid, expireDate);
            }

            var mail = target.getMail();

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(mail);
            simpleMailMessage.setSubject("[METABOBANK]プロジェクト招待通知");
            simpleMailMessage.setText(new AuthService.MailTemplate().process(uid));

            this.mailSender.send(simpleMailMessage);
        }
    }

    /**
     * アカウントのプロフィールを更新するメソッド.
     *
     * <p>
     * t_userを検索し、対象のアカウントのプロフィールを更新する
     * </p>
     *
     * @param accountUuid
     * @param profileInfo
     *
     * @return void
     *
     **/
    public void updateProfile(
            final UUID accountUuid,
            final ProfileInfo profileInfo) {
        var profile = JsonMapper.stringify(profileInfo);
        this.userDao.updateProfileByAccountUUID(accountUuid, profile);
    }

    /**
     * アクセストークンをキーにaccountUUIDを取得するメソッド.
     *
     * <p>
     * アクセストークンをキーにOpenAMからuidを取得する。<br>
     * uidをキーにt_accountを検索しaccountUUIDを取得する。
     * </p>
     *
     * @param accessToken
     *
     * @return accountUUID
     *
     **/
    public UUID getAccountUUID( final String accessToken ) {
        var tokenInfo = this.authModule.getTokenInfo( accessToken );

        if(null == tokenInfo) {
            throw new RestApiException(HttpStatus.UNAUTHORIZED);
        }

        var uid       = tokenInfo.getUid();

        var account   = new TAccountDataReader( accountDao.readByUid( uid ) );

        return account.uuid();
    }

    private class MailTemplate {

        private String process( final String uid ) {
            Context context = new Context();
            context.setVariable( "uid", uid );

            // FIXME テンプレート
            return springTemplateEngine.process( "invitemail", context );
        }
    }
}
