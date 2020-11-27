package ddbjld.api.app.core.module;

import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.common.annotation.Module;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Module
@Slf4j
@AllArgsConstructor
public class FileModule {

    private ConfigSet config;

    @PostConstruct
    public void setUp() {
        // Spring起動時に各DB用のディレクトリが作られていなかったら作成する
        var rootDir       = this.getPath(this.config.file.path.ROOT);
        var jvarDir       = this.getPath(this.config.file.path.JVAR);
        var bioProjectDir = this.getPath(this.config.file.path.BIO_PROJECT);
        var bioSampleDir  = this.getPath(this.config.file.path.BIO_SAMPLE);
        var tradDir       = this.getPath(this.config.file.path.TRAD);

        if(false == this.exists(rootDir)) {
            this.createDirectory(rootDir);
        }

        if(false == this.exists(jvarDir)) {
            this.createDirectory(jvarDir);
        }

        if(false == this.exists(bioProjectDir)) {
            this.createDirectory(bioProjectDir);
        }

        if(false == this.exists(bioSampleDir)) {
            this.createDirectory(bioSampleDir);
        }

        if(false == this.exists(tradDir)) {
            this.createDirectory(tradDir);
        }
    }

    public boolean exists(final Path path) {
        return Files.exists(path);
    }

    public boolean createDirectory(final Path dir) {
        try {
            Files.createDirectory(dir);

            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean upload(final byte[] file, final Path path) {
        try (OutputStream os = Files.newOutputStream(path, StandardOpenOption.CREATE)) {
            os.write(file);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public ByteArrayResource download(final Path path) {
        try {
            var bytes = Files.readAllBytes(path);

            return new ByteArrayResource(bytes);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public boolean delete(final Path path) {
        try {
            Files.delete(path);

            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public byte[] toByte(final MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public boolean validateFileType(
            final String type,
            final String name) {
        //  workbookでかつ.xlsx
        //  もしくはvcfでかつVCFでありえる拡張子だったらOK
        var isWorkBook = type.equals("WORKBOOK")
                && name.matches(".*.xlsx");

        // .gzなどで圧縮している場合もありえるため、正規表現の末尾は.*.とした
        var isVCF = type.equals("VCF")
                && (name.matches(".*.vcf") || name.matches(".*.vcf.*."));

        return isWorkBook || isVCF;
    }


    public boolean validateFileType(
            final MultipartFile file,
            final String type,
            final String name) {
        var original = file.getOriginalFilename();

        //  workbookでかつ.xlsx
        //  もしくはvcfでかつVCFでありえる拡張子だったらOK
        //  vcfはgzの圧縮のみとする
        var isWorkBook = type.equals("WORKBOOK")
                      && name.matches(".*\\.xlsx")
                      && original.matches(".*\\.xlsx");

        var isVcf = type.equals("VCF")
                 && name.matches(".*\\.vcf")
                 && original.matches(".*\\.vcf");

        var isVcfGz = type.equals("VCF")
                   && name.matches(".*\\.vcf.gz")
                   && original.matches(".*\\.vcf.gz");

        return isWorkBook || isVcf || isVcfGz;
    }

    public Path getPath(String... nodes) {
        var sb = new StringBuilder();

        for(String node : nodes) {
            sb.append("/");
            sb.append(node);
        }

        return Paths.get(sb.toString());
    }

    public String getFileNameWithRevision(String fileName, int revision) {
        var isWorkBook = fileName.matches(".*\\.xlsx");
        var isVCF      = fileName.matches(".*\\.vcf") || fileName.matches(".*\\.vcf.*.");

        var extension  = isWorkBook
                ?".xlsx"
                : isVCF
                    ? fileName.substring(fileName.indexOf(".vcf"))
                : null;

        if (null == extension) {
            return null;
        }

        return fileName.replace(extension, "") + ".R" + revision + extension;
    }

    public String convertSpace(String name) {
        // ファイル操作の際にファイル名のスペースが邪魔になることがあるので変換
        return name
                .replaceAll("\\s", "\\\\ ")
                .replace("　", "\\　");
    }
}
