package com.ddbj.ld.app.core.module;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.common.annotation.Module;
import com.ddbj.ld.common.exception.DdbjException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.regex.Pattern;

@Module
@AllArgsConstructor
@Slf4j
public class FileModule {

    private final FTPClient ftpClient;

    private ConfigSet config;

    @PostConstruct
    public void setUp() {
        // Spring起動時にデータ格納用ディレクトリが作られていなかったら作成する
        this.createDirectory(this.config.file.path.outDir);
        this.createDirectory(this.config.file.path.dataDir);
        this.createDirectory(this.config.file.path.publicDir);

        // JGA
        this.createDirectory(this.config.file.path.jga.basePath);

        // BioProject
        this.createDirectory(this.config.file.path.bioProject.basePath);
        this.createDirectory(this.config.file.path.bioProject.fullPath);
        this.createDirectory(this.config.file.path.bioProject.fullXMLPath);

        // BioSample
        this.createDirectory(this.config.file.path.bioSample.basePath);
        this.createDirectory(this.config.file.path.bioSample.fullPath);
        this.createDirectory(this.config.file.path.bioSample.fullXMLPath);

        // SRA
        this.createDirectory(this.config.file.path.sra.basePath);
        this.createDirectory(this.config.file.path.sra.fullPath);
        this.createDirectory(this.config.file.path.sra.fullXMLPath);
        this.createDirectory(this.config.file.path.sra.accessionsPath);
        this.createDirectory(this.config.file.path.sra.ncbi);
        this.createDirectory(this.config.file.path.sra.ebi);
    }

    private class RemoveRecurseFileVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            return delete(file);
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            return delete(dir);
        }

        private FileVisitResult delete(Path path) {
            try {
                Files.deleteIfExists(path);
                return FileVisitResult.CONTINUE;
            } catch (IOException e) {
                var message = "Deleting recursively is failed.";
                log.error(message, e);

                throw new DdbjException(message);
            }
        }
    }

    public boolean exists(final String path) {
        return Files.exists(this.getPath(path));
    }

    public void createDirectory(final String path) {
        try {
            if(!this.exists(path)) {
                Files.createDirectories(this.getPath(path));
            }
        } catch (IOException e) {
            var message = "Creating directory is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void delete(final String path) {
        try {
            Files.deleteIfExists(this.getPath(path));
        } catch (IOException e) {
            var message = "Deleting file is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void deleteRecursively(final String path) {
        try {
            if (this.exists(path)) {
                Files.walkFileTree(this.getPath(path), new RemoveRecurseFileVisitor());
            }
        } catch (IOException e) {
            var message = "Deleting recursively is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void overwrite(final String path, final String content) {
        try {
            // 上書き
            this.delete(path);
            Files.createFile(this.getPath(path));
            Files.writeString(this.getPath(path), content);

        } catch (IOException e) {
            var message = "Writing file is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public Path getPath(String... nodes) {
        var sb = new StringBuilder();

        for(String node : nodes) {
            sb.append("/");
            sb.append(node);
        }

        return Paths.get(sb.toString());
    }

    public void compressToTarGz (
            final String target
    ) {
        var dir  = Paths.get(target);
        var dest = Paths.get("out/" + dir.getFileName() + ".tar.gz");

        try (var os = Files.newOutputStream(dest);
             var gos = new GzipCompressorOutputStream(os);
             var out = new TarArchiveOutputStream(gos);
             var stream = Files.walk(dir)) {

            // forを回すためにiterableとする
            Iterable<Path> iterable = stream::iterator;

            for (var p : iterable) {
                var path = p.toFile().getPath();

                if(target.equals(path)) {
                    // target自体は圧縮の対象から外す
                    continue;
                }

                var entry = out.createArchiveEntry(
                        p.toFile(),
                        // target自体は圧縮せず、配下のフォルダ・ファイルのみを圧縮する
                        path.replace(target, ""));

                out.putArchiveEntry(entry);

                if (p.toFile().isFile()) {
                    try (InputStream i = Files.newInputStream(p)) {
                        IOUtils.copy(i, out);
                    }
                }

                out.closeArchiveEntry();
            }

            out.finish();

        } catch (IOException e) {
            var message = "Compressing to tar.gz is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void extractGZIP(
            final String target,
            final String dist
    ) {
        try (var in = new GzipCompressorInputStream(Files.newInputStream(this.getPath(target)));
             var out= Files.newOutputStream(this.getPath(dist))) {

            int len;
            byte[] b = new byte[1024 * 4];
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }

        } catch (IOException e) {
            var message = "Extracting gz is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void extractSRA(
            final String target,
            final String dist
    ) {
        try (var fi = Files.newInputStream(Paths.get(target));
             var gzi = new GzipCompressorInputStream(fi);
             var in = new TarArchiveInputStream(gzi)) {

            var regex   = "^([ES]RA)(\\d{3})\\d+";
            var pattern = Pattern.compile(regex);

            ArchiveEntry entry;
            File file;

            while ((entry = in.getNextEntry()) != null) {

                if (!in.canReadEntryData(entry)) {
                    continue;
                }

                var m = pattern.matcher(entry.getName());

                if (m.find()) {
                    var firstDir   = dist + "/" + m.group(1);
                    var extractDir = dist + "/" + m.group(1) + "/" + m.group(1) + m.group(2);

                    this.createDirectory(firstDir);
                    this.createDirectory(extractDir);

                    file = Paths.get(extractDir).resolve(entry.getName()).toFile();

                } else if ("SRA_Accessions".equals(entry.getName())){
                    file = Paths.get(this.config.file.path.sra.accessions).toFile();
                } else {
                    continue;
                }

                if(entry.isDirectory()) {
                    file.mkdir();
                } else {
                    try (var o = Files.newOutputStream(file.toPath())) {
                        // ファイルを書き込む
                        IOUtils.copy(in, o);
                    }
                }
            }
        } catch (IOException e) {
            var message = "Extracting SRA is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public String getExecDate() {

        try {
            return Files.readString(this.getPath(this.config.file.path.sra.execDatePath));
        } catch (IOException e) {
            var message = "Getting exec date is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void login(final String hostName) {

        try {
            this.ftpClient.connect(hostName);
            this.ftpClient.enterLocalPassiveMode();
            // 匿名でログイン
            this.ftpClient.login("anonymous", "");
            this.ftpClient.enterLocalPassiveMode();
            this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        } catch (IOException e) {
            var message = "Connecting FTP is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void logout() {

        try {
            this.ftpClient.logout();
            this.ftpClient.disconnect();

        } catch (IOException e) {
            var message = "Disconnecting FTP is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void retrieveFile(
            final String hostname,
            final String target,
            final String dist
    ) {

        try (var out = new FileOutputStream(dist)){
            this.login(hostname);
            this.ftpClient.retrieveFile(target, out);
            this.logout();
        } catch (IOException e) {
            var message = "Getting from FTP is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public FTPFile[] listFiles(
            final String hostname,
            final String targetDir
    ) {

        try {
            this.login(hostname);
            var fileList = this.ftpClient.listFiles(targetDir);
            this.logout();

            if(null == fileList) {
                return null;
            }

            Arrays.sort(fileList, (file1, file2) -> {
                if(file1.getTimestamp().after(file2.getTimestamp())) {
                    return 1;
                } else {
                    return -1;
                }
            });

            return fileList;

        } catch (IOException e) {
            var message = "Searching FTP is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public boolean exists(
            final String hostname,
            final String targetDir,
            final String fileName
    ) {

        var fileList = this.listFiles(hostname, targetDir);

        if(null == fileList) {
            return false;
        }

        for(var file: fileList) {
            if(fileName.equals(file.getName())) {
                return true;
            }
        }

        return false;
    }

    public boolean existsDir(
            final String hostname,
            final String targetDir
    ) {

        try {
            this.login(hostname);
            var fileList = this.ftpClient.listFiles(targetDir);
            this.logout();

            return null != fileList;

        } catch (IOException e) {
            var message = "Searching FTP is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }
}
