package ddbjld.api.data.beans;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserInfo {
    private String accessToken;
    private String uid;
    private String mail;
    private UUID accountUuid;
    private boolean admin;
    private ProfileInfo profile;
    private List<RoleInfo> role;
    private List<ProjectViewData> project;
}
