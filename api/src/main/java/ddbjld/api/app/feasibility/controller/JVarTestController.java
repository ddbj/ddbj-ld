package ddbjld.api.app.feasibility.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

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

    @RequestMapping(value = "account/{uid}", method = RequestMethod.POST)
    public UUID registerTestAccount(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @PathVariable("uid") String uid)
        final var sql = "INSERT INTO t_account "
                + "VALUES ( gen_random_uuid(), ?, ? )"
                + "RETURNING uuid";

        Object[] args = {
                uid,
                null,
        };

        Map<String, Object> returned = this.jvarJdbc.queryForMap( sql, args );
        return (UUID)returned.get( "uuid" );
    }
}
