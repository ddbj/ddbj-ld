package ddbjld.api.testdata.resources.riken_cleaned_json_plus;

import ddbjld.api.maintenance.SystemMaintenanceService;
import ddbjld.api.app.service.ProjectService;
import ddbjld.api.app.service.dao.AccountDao;
import ddbjld.api.common.utility.JsonMapper;
import ddbjld.api.data.json.ExcelSheetData;
import ddbjld.api.data.values.DraftMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

@SpringBootTest
public class ImportDataTest {

    // 任意で変えるとよさげ
    private final String uid = "user.0";
    private final String FILE_PATH = "./src/test/java/ddbj/testdata/resources/riken_cleaned_json_plus";

    @Autowired
    ProjectService projectService;
    
    @Autowired
    SystemMaintenanceService systemMaintenanceService;

    @Autowired
    AccountDao accountDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void importData() {
        jdbcTemplate.execute("TRUNCATE TABLE t_project CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE t_project_role CASCADE");

        var reader = new AccountDao.TAccountDataReader(accountDao.readByUid(uid));
        var accountUuid = reader.uuid();

        Path parent = Paths.get(FILE_PATH);

        try {
            Files.list(parent).forEach(new Consumer<Path>() {
                @Override
                public void accept(Path child){
                    try {
                        var json = Files.readString(child);
                        DraftMetadata data = JsonMapper.parse( json, DraftMetadata.class );
                        ExcelSheetData[] metadata   = data.metadata;
                        ExcelSheetData.MetadataObjects projectSheet = metadata[0].data[0];
                        var projectId = projectSheet.keySet().iterator().next();

                        if(projectId.equals("RPMM0059")) {
                            System.out.println(json);
                        }

                        systemMaintenanceService.putProjectData(accountUuid, projectId, metadata);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
