package ddbjld.api.data.beans;

import lombok.Data;

import java.util.List;

@Data
public class FileInfo {
    private String projectId;
    private List<FileInfo>  files;
}
