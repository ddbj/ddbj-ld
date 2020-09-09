package ddbjld.api.data.beans;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
// JsonNamingは不要
public class InvitationTargetInfo {
    @NotBlank
    private String uid;
    @NotBlank
    private String mail;
    private LocalDate expireDate;
}
