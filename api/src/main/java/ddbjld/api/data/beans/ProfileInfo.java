package ddbjld.api.data.beans;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
// JsonNamingは不要
public class ProfileInfo {
    // FIXME プロフィールの項目は未確定
    private String displayName;
    // en or ja
    @NotBlank
    private String lang;
}
