package ddbjld.api.app.transact.dao;

import ddbjld.api.common.utility.SpringJdbcUtil;
import ddbjld.api.data.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Repository
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
@Slf4j
public class UserDao {
    private JdbcTemplate jvarJdbc;

    @Transactional(readOnly = true)
    public UserEntity read(final UUID uuid) {
        var sql = "SELECT * FROM t_user WHERE uuid = ?;";
        Object[] args = {
                uuid
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        if(null == row) {
            return null;
        }

        return this.getEntity(row);
    }

    @Transactional(readOnly = true)
    public UserEntity readByAccountUUID(final UUID accountUUID) {
        var sql = "SELECT * FROM t_user WHERE account_uuid = ?;";
        Object[] args = {
                accountUUID
        };

        var row = SpringJdbcUtil.MapQuery.one(this.jvarJdbc, sql, args);

        if(null == row) {
            return null;
        }

        return this.getEntity(row);
    }

    @Transactional(readOnly = true)
    public boolean exists(final UUID uuid) {
        var sql = "SELECT * FROM t_user WHERE uuid = ?;";
        Object[] args = {
                uuid
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    @Transactional(readOnly = true)
    public boolean existByAccountUUID(final UUID accountUUID) {
        var sql = "SELECT * FROM t_user WHERE account_uuid = ?;";
        Object[] args = {
                accountUUID
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

<<<<<<< HEAD
    @Transactional(readOnly = true)
    public boolean isCurator(final UUID accountUUID) {
        var sql = "SELECT * FROM t_user WHERE account_uuid = ? AND curator = true;";
        Object[] args = {
                accountUUID
        };

        return SpringJdbcUtil.MapQuery.exists(this.jvarJdbc, sql, args);
    }

    public void insert(final UUID accountUUID) {
        var sql = "INSERT INTO t_user (uuid, account_uuid, curator) VALUES (gen_random_uuid(), ?, false)";
=======
    public void insert(final UUID accountUUID) {
        var sql = "INSERT INTO t_user (uuid, account_uuid, admin) VALUES (gen_random_uuid(), ?, false)";
>>>>>>> 差分修正
        Object[] args = {
                accountUUID
        };

        this.jvarJdbc.update(sql, args);
    }

<<<<<<< HEAD
    public void updateCurator(final UUID uuid, final boolean curator) {
        var sql = "UPDATE t_user SET curator = ? WHERE uuid = ?";
        Object[] args = {
                curator,
=======
    public void updateAdmin(final UUID uuid, final boolean admin) {
        var sql = "UPDATE t_user SET admin = ? WHERE uuid = ?";
        Object[] args = {
                admin,
>>>>>>> 差分修正
                uuid
        };

        this.jvarJdbc.update(sql, args);
    }

    private UserEntity getEntity(final Map<String, Object> row) {
        return new UserEntity(
                (UUID)row.get("uuid"),
                (UUID)row.get("account_uuid"),
<<<<<<< HEAD
                (boolean)row.get("curator")
=======
                (boolean)row.get("admin")
>>>>>>> 差分修正
        );
    }
}
