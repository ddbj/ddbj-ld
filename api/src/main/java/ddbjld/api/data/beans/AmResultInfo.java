package ddbjld.api.data.beans;

import lombok.Data;

import java.util.List;

@Data
// JsonNamingは不要
public class AmResultInfo {
    private List<AmUserInfo> result;
}
