package ddbjld.api.app.feasibility.transact.service;

import ddbjld.api.app.core.module.FileModule;
import ddbjld.api.app.service.dao.FileDao;
import ddbjld.api.app.service.FileService;
import ddbjld.api.app.service.dao.AccountDao;
import ddbjld.api.app.service.dao.ProjectDao;
import ddbjld.api.app.service.dao.ProjectRoleDao;
import ddbjld.api.app.service.dao.UserDao;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

@SpringBootTest
public class FileServiceTest {
    @Autowired @Qualifier("jvarJdbc")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FileService fileService;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectRoleDao projectRoleDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private FileModule fileModule;

    private String uid;

    private String projectFileType;

    private String projectFileName;

    private String rawFileType;

    private String rawFileName;

    private String analysisFileType;

    private String analysisFileName;

    private byte[] fileContent;

    public FileServiceTest() {
        this.uid = "user.0";

        this.projectFileType = "project";
        this.projectFileName = "project.txt";
        this.rawFileType = "raw";
        this.rawFileName = "raw.txt";
        this.analysisFileType = "analysis";
        this.analysisFileName = "analysis.txt";

        this.fileContent = "test".getBytes();
    }

    @Test
    public void getProjectFileTest_公開データ() {
        var projectIds          = this.projectDao.create();

        final var sql = "UPDATE t_project SET first_published_at = current_timestamp WHERE uuid = ?";
        Object[] args = { projectIds.uuid };

        this.jdbcTemplate.update(sql, args);

        this.fileDao.create(projectIds.uuid, this.projectFileType, this.projectFileName);
        this.fileDao.create(projectIds.uuid, this.rawFileType, this.rawFileName);
        this.fileDao.create(projectIds.uuid, this.analysisFileType, this.analysisFileName);

        this.fileModule.delete(projectIds.id);
        this.fileModule.createDirectory(projectIds.id);
        this.fileModule.createDirectory(projectIds.id, this.projectFileType);
        this.fileModule.createDirectory(projectIds.id, this.rawFileType);
        this.fileModule.createDirectory(projectIds.id, this.analysisFileType);

        this.fileModule.upload(this.fileContent, projectIds.id, this.projectFileType, this.projectFileName);
        this.fileModule.upload(this.fileContent, projectIds.id, this.rawFileType, this.rawFileName);
        this.fileModule.upload(this.fileContent, projectIds.id, this.analysisFileType, this.analysisFileName);

        var fileList = this.fileService.getProjectFile(projectIds.id);

        assertEquals(3, fileList.size());
        System.out.println("getProjectFileTest_公開データ成功");
    }

    @Test
    public void getProjectFileTest_編集中データ() {
        var account      = new AccountDao.TAccountDataReader(this.accountDao.readByUid(this.uid));
        var accountUuid = account.uuid();

        var projectIds           = this.projectDao.create();
        LocalDate expireDate     = LocalDate.of(2999, 12, 31);
        this.projectRoleDao.createEditor(projectIds.uuid, accountUuid, expireDate);
        this.projectDao.updateEditingFlag(projectIds.uuid, true);

        this.fileDao.create(projectIds.uuid, this.projectFileType, this.projectFileName);
        this.fileDao.create(projectIds.uuid, this.rawFileType, this.rawFileName);
        this.fileDao.create(projectIds.uuid, this.analysisFileType, this.analysisFileName);

        this.fileModule.delete(projectIds.id);
        this.fileModule.createDirectory(projectIds.id);
        this.fileModule.createDirectory(projectIds.id, "draft");
        this.fileModule.createDirectory(projectIds.id, "draft", this.projectFileType);
        this.fileModule.createDirectory(projectIds.id, "draft", this.rawFileType);
        this.fileModule.createDirectory(projectIds.id, "draft", this.analysisFileType);

        this.fileModule.upload(this.fileContent, projectIds.id, "draft", this.projectFileType, this.projectFileName);
        this.fileModule.upload(this.fileContent, projectIds.id, "draft", this.rawFileType, this.rawFileName);
        this.fileModule.upload(this.fileContent, projectIds.id, "draft", this.analysisFileType, this.analysisFileName);

        var fileList = this.fileService.getProjectFile(accountUuid, projectIds.id);

        assertEquals(3, fileList.size());
        System.out.println("getProjectFileTest_編集中データ成功");
    }

    @Test
    public void upload() {
        var account     = new AccountDao.TAccountDataReader(this.accountDao.readByUid(this.uid));
        var accountUuid = account.uuid();

        var projectIds           = this.projectDao.create();
        LocalDate expireDate     = LocalDate.of(2999, 12, 31);
        this.projectRoleDao.createEditor(projectIds.uuid, accountUuid, expireDate);
        this.projectDao.updateEditingFlag(projectIds.uuid, true);

        var fileType = "upload";
        var fileName = "test.txt";
        var file = "test".getBytes();
        var token = this.fileService.preUpload(accountUuid, projectIds.id, fileType, fileName);
        this.fileService.upload(projectIds.id, fileType, fileName, file, token.getUploadToken());

        assertTrue(this.fileModule.exists(projectIds.id, "draft", fileType, fileName));
        System.out.println("upload成功");
    }

    @Test
    public void download_公開データ() {
        var projectIds = this.projectDao.create();

        final var sql = "UPDATE t_project SET first_published_at = current_timestamp WHERE uuid = ?";
        Object[] args = { projectIds.uuid };

        this.jdbcTemplate.update(sql, args);

        var fileType = "download";
        var fileName = "test.txt";
        var file     = "test".getBytes();

        this.fileDao.create(projectIds.uuid, fileType, fileName);
        this.fileModule.createDirectory(projectIds.id);
        this.fileModule.createDirectory(projectIds.id, fileType);
        this.fileModule.upload(file, projectIds.id, fileType, fileName);
        var result = this.fileService.download(projectIds.id, fileType, fileName);

        assertNotEquals(0, result.length);
        System.out.println("download_公開データ成功");
    }

    @Test
    public void download_編集中データ() {
        var account      = new AccountDao.TAccountDataReader(this.accountDao.readByUid(this.uid));
        var accountUuid = account.uuid();

        var projectIds           = this.projectDao.create();
        LocalDate expireDate     = LocalDate.of(2999, 12, 31);
        this.projectRoleDao.createEditor(projectIds.uuid, accountUuid, expireDate);
        this.projectDao.updateEditingFlag(projectIds.uuid, true);

        this.fileDao.create(projectIds.uuid, this.projectFileType, this.projectFileName);
        this.fileDao.create(projectIds.uuid, this.rawFileType, this.rawFileName);
        this.fileDao.create(projectIds.uuid, this.analysisFileType, this.analysisFileName);

        this.fileModule.delete(projectIds.id);
        this.fileModule.createDirectory(projectIds.id);

        var fileType = "download";
        var fileName = "test.txt";
        var file     = "test".getBytes();

        this.fileDao.create(projectIds.uuid, fileType, fileName);
        this.fileModule.createDirectory(projectIds.id);

        this.fileModule.createDirectory(projectIds.id, "draft");
        this.fileModule.createDirectory(projectIds.id, "draft", fileType);
        this.fileModule.upload(file, projectIds.id, "draft", fileType, fileName);
        var result = this.fileService.download(accountUuid, projectIds.id, fileType, fileName);

        assertNotEquals(0, result.length);
        System.out.println("download_編集中データ成功");
    }

    @Test
    public void delete() {
        var account      = new AccountDao.TAccountDataReader(this.accountDao.readByUid(this.uid));
        var accountUuid  = account.uuid();

        var projectIds           = this.projectDao.create();
        LocalDate expireDate     = LocalDate.of(2999, 12, 31);
        this.projectRoleDao.createEditor(projectIds.uuid, accountUuid, expireDate);
        this.projectDao.updateEditingFlag(projectIds.uuid, true);

        this.fileDao.create(projectIds.uuid, this.projectFileType, this.projectFileName);
        this.fileDao.create(projectIds.uuid, this.rawFileType, this.rawFileName);
        this.fileDao.create(projectIds.uuid, this.analysisFileType, this.analysisFileName);

        this.fileModule.delete(projectIds.id);
        this.fileModule.createDirectory(projectIds.id);

        var fileType = "download";
        var fileName = "test.txt";
        var file     = "test".getBytes();

        this.fileDao.create(projectIds.uuid, fileType, fileName);
        this.fileModule.createDirectory(projectIds.id);

        this.fileModule.createDirectory(projectIds.id, "draft");
        this.fileModule.createDirectory(projectIds.id, "draft", fileType);
        this.fileModule.upload(file, projectIds.id, "draft", fileType, fileName);

        assertTrue(this.fileModule.exists(projectIds.id, "draft", fileType, fileName));

        this.fileService.delete(accountUuid, projectIds.id, fileType, fileName);

        assertFalse(this.fileModule.exists(projectIds.id, "draft", fileType, fileName));

        System.out.println("delete成功");
    }

    @Test
    public void publish() {
        var account      = new AccountDao.TAccountDataReader(this.accountDao.readByUid(this.uid));
        var accountUuid  = account.uuid();
        var user         = new UserDao.TUserDataReader(this.userDao.readByAccountUuid(accountUuid));
        var userUuid     = user.uuid();

        this.userDao.updateAdminable(userUuid,true);

        var projectIds           = this.projectDao.create();
        this.projectDao.updateEditingFlag(projectIds.uuid, false);

        this.fileDao.create(projectIds.uuid, this.projectFileType, this.projectFileName);
        this.fileDao.create(projectIds.uuid, this.rawFileType, this.rawFileName);
        this.fileDao.create(projectIds.uuid, this.analysisFileType, this.analysisFileName);

        this.fileModule.delete(projectIds.id);
        this.fileModule.createDirectory(projectIds.id);
        this.fileModule.createDirectory(projectIds.id, "draft");
        this.fileModule.createDirectory(projectIds.id, "draft", this.projectFileType);
        this.fileModule.createDirectory(projectIds.id, "draft", this.rawFileType);
        this.fileModule.createDirectory(projectIds.id, "draft", this.analysisFileType);

        this.fileModule.upload(this.fileContent, projectIds.id, "draft", this.projectFileType, this.projectFileName);
        this.fileModule.upload(this.fileContent, projectIds.id, "draft", this.rawFileType, this.rawFileName);
        this.fileModule.upload(this.fileContent, projectIds.id, "draft", this.analysisFileType, this.analysisFileName);

        this.fileService.publish(accountUuid, projectIds.id);

        assertFalse(this.fileModule.exists(projectIds.id, "draft"));

        assertTrue(this.fileModule.exists(projectIds.id, this.projectFileType, this.projectFileName));
        assertTrue(this.fileModule.exists(projectIds.id, this.rawFileType, this.rawFileName));
        assertTrue(this.fileModule.exists(projectIds.id, this.analysisFileType, this.analysisFileName));
    }

    @Test
    public void discard() {
        var account      = new AccountDao.TAccountDataReader(this.accountDao.readByUid(this.uid));
        var accountUuid  = account.uuid();
        var user         = new UserDao.TUserDataReader(this.userDao.readByAccountUuid(accountUuid));
        var userUuid     = user.uuid();

        this.userDao.updateAdminable(userUuid,true);

        var projectIds           = this.projectDao.create();
        this.projectDao.updateEditingFlag(projectIds.uuid, false);

        this.fileDao.create(projectIds.uuid, this.projectFileType, this.projectFileName);
        this.fileDao.create(projectIds.uuid, this.rawFileType, this.rawFileName);
        this.fileDao.create(projectIds.uuid, this.analysisFileType, this.analysisFileName);

        this.fileModule.delete(projectIds.id);
        this.fileModule.createDirectory(projectIds.id);
        this.fileModule.createDirectory(projectIds.id, "draft");
        this.fileModule.createDirectory(projectIds.id, "draft", this.projectFileType);
        this.fileModule.createDirectory(projectIds.id, "draft", this.rawFileType);
        this.fileModule.createDirectory(projectIds.id, "draft", this.analysisFileType);

        this.fileModule.upload(this.fileContent, projectIds.id, "draft", this.projectFileType, this.projectFileName);
        this.fileModule.upload(this.fileContent, projectIds.id, "draft", this.rawFileType, this.rawFileName);
        this.fileModule.upload(this.fileContent, projectIds.id, "draft", this.analysisFileType, this.analysisFileName);

        this.fileService.discard(accountUuid, projectIds.id);

        assertFalse(this.fileModule.exists(projectIds.id, "draft"));
    }
}
