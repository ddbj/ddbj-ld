package ddbjld.api.app.transact.dao;

import ddbjld.api.common.utility.SpringJdbcUtil;
import ddbjld.api.data.entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
@Slf4j
public class AccountDao {
    private JdbcTemplate jvarJdbc;

    @Transactional(readOnly = true)
    public AccountEntity read(final UUID uuid) {
        var sql = "SELECT * FROM t_account WHERE uuid = ?;";
        Object[] args = {
                uuid
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        if(null == row) {
            return null;
        }

        return new AccountEntity(
                (UUID)row.get("uuid"),
                (String)row.get("uid"),
                (UUID)row.get("refresh_token")
        );
    }

    @Transactional(readOnly = true)
    public AccountEntity readByUID(final String uid) {
        var sql = "SELECT * FROM t_account WHERE uid = ?;";
        Object[] args = {
                uid
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        if(null == row) {
            return null;
        }

        return new AccountEntity(
                (UUID)row.get("uuid"),
                (String)row.get("uid"),
                (UUID)row.get("refresh_token")
        );
    }

    @Transactional(readOnly = true)
    public boolean exists(final UUID uuid) {
        var sql = "SELECT * FROM t_account WHERE uuid = ?;";
        Object[] args = {
                uuid
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    @Transactional(readOnly = true)
    public boolean existByUid(final String uid) {
        var sql = "SELECT * FROM t_account WHERE uid = ?;";
        Object[] args = {
                uid
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    public void insert(final String uid, final UUID refreshToken) {
        var sql = "INSERT INTO t_account (uuid, uid, refresh_token) VALUES (gen_random_uuid(), ?, ?)";
        Object[] args = {
                uid,
                refreshToken
        };

        this.jvarJdbc.update(sql, args);
    }

    public void updateRefreshToken(final UUID uuid, final UUID refreshToken) {
        var sql = "UPDATE t_account SET refresh_token = ? WHERE uuid = ?";
        Object[] args = {
                refreshToken,
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    public void updateRefreshTokenByUid(final String uid, final UUID refreshToken) {
        var sql = "UPDATE t_account SET refresh_token = ? WHERE uid = ?";
        Object[] args = {
                refreshToken,
                uid
        };

        this.jvarJdbc.update(sql, args);
    }
}
