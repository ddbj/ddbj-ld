package ddbjld.api.data.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectMemberList {
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class ProjectMember {
		private String uid;
		private UUID accountUUID;
		private UUID userUUID;
		private boolean enabled;
		private boolean expired;
		private LocalDate expireDate;
		private LocalDateTime createdAt;
		private String profile; //FIXME：ここは後でProfileInfoに変更。
	}
	
	/** プロジェクトID（t_project.id） */
	private String projectId;
	/** プロジェクトUUID（t_project.uuid） */
	private UUID projectUUID;
	
	/** プロジェクト登録者（所有格権限） */
	private ProjectMember owner;
	/** 共同編集者 */
	private List<ProjectMember> editors;
	/** 共同閲覧者【仕様凍結中】 */
	@Deprecated
	private List<ProjectMember> observers;
}
