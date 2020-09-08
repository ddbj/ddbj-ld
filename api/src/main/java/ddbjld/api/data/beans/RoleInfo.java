package ddbjld.api.data.beans;

import lombok.Data;

import java.sql.Date;

@Data
public class RoleInfo {
    private String projectId;
    private boolean owner;
    private boolean writable;
    private boolean readable;
    private Date expireDate;
}
