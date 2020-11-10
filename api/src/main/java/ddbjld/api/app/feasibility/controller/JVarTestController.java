package ddbjld.api.app.feasibility.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping({
        "test/jvar",
})
@RestController
public class JVarTestController {
    @Autowired @Qualifier("publicJdbc")
    private JdbcTemplate publicJdbc;

    @Autowired @Qualifier("jvarJdbc")
    private JdbcTemplate jvarJdbc;

    @RequestMapping(value = "public_db", method = RequestMethod.GET)
    public String testPublicDataSource() {
        var dataSource = publicJdbc.getDataSource();

        return "DataSource:" + dataSource;
    }

    @RequestMapping(value = "jvar_db", method = RequestMethod.GET)
    public String testDataSourceJVar() {
        var dataSource = jvarJdbc.getDataSource();

        return "DataSource:" + dataSource;
    }
}
