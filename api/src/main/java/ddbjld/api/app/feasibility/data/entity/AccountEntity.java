package ddbjld.api.app.feasibility.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountEntity {
    private UUID uuid;
    private String uid;
    private UUID refreshToken;
}
