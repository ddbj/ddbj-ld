package ddbjld.api.app.core.module;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.app.feasibility.common.annotation.Module;
import ddbjld.api.common.utility.UrlBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Module
@Slf4j
@AllArgsConstructor
public class FileModule {

    private ConfigSet config;

    @PostConstruct
    public void setUp() {
        // Spring起動時に各DB用のディレクトリが作られていなかったら作成する
        var rootJVar       = config.nextcloud.endpoints.ROOT_JVAR;
        var rootBioProject = config.nextcloud.endpoints.ROOT_BIOPROJECT;
        var rootBioSample  = config.nextcloud.endpoints.ROOT_BIOSAMPLE;
        var rootTrad       = config.nextcloud.endpoints.ROOT_TRAD;


        if(false == this.exists(rootJVar)) {
            this.createDirectory(rootJVar);
        }

        if(false == this.exists(rootBioProject)) {
            this.createDirectory(rootBioProject);
        }

        if(false == this.exists(rootBioSample)) {
            this.createDirectory(rootBioSample);
        }

        if(false == this.exists(rootTrad)) {
            this.createDirectory(rootTrad);
        }
    }

    private Sardine begin() {
        var admin    = config.nextcloud.client.ADMIN;
        var password = config.nextcloud.client.ADMIN_PASS;

        return SardineFactory.begin(admin, password);
    }

    public boolean exists(final String... path) {
        var sardine = this.begin();
        var url     = UrlBuilder.url(config.nextcloud.endpoints.URL).path(path).build();

        try {
            return sardine.exists(url);
        } catch (IOException e) {
            log.debug(e.getMessage());

            return false;
        }
    }

    public List<DavResource> list(final String... path) {
        var sardine = this.begin();
        var url     = UrlBuilder.url(config.nextcloud.endpoints.URL).path(path).build();

        try {
            return sardine.list(url);
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    public boolean createDirectory(final String... path) {
        var sardine = this.begin();
        var url     = UrlBuilder.url(config.nextcloud.endpoints.URL).path(path).build();

        try  {
            sardine.createDirectory(url);

            return true;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return false;
        }
    }

    public boolean upload(final byte[] file, final String... path) {
        var sardine = this.begin();
        var url     = UrlBuilder.url(config.nextcloud.endpoints.URL).path(path).build();

        try  {
            sardine.put(url, file);

            return true;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return false;
        }
    }

    public byte[] download(final String... path) {
        var sardine = this.begin();
        var url     = UrlBuilder.url(config.nextcloud.endpoints.URL).path(path).build();

        try(InputStream inputStream = sardine.get(url)) {
            var result = IOUtils.toByteArray(inputStream);

            return result;
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    public boolean delete(final String... path) {
        var sardine = this.begin();
        var url     = UrlBuilder.url(config.nextcloud.endpoints.URL).path(path).build();

        try {
            sardine.delete(url);

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean move(final String path, final String distPath) {
        var sardine = this.begin();
        var url        = UrlBuilder.url(config.nextcloud.endpoints.URL).path(path).build();
        var distUrl    = UrlBuilder.url(config.nextcloud.endpoints.URL).path(distPath).build();

        try {
            sardine.move(url, distUrl);

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean copy(final String path, final String distPath) {
        var sardine = this.begin();
        var url        = UrlBuilder.url(config.nextcloud.endpoints.URL).path(path).build();
        var distUrl    = UrlBuilder.url(config.nextcloud.endpoints.URL).path(distPath).build();

        try {
            sardine.copy(url, distUrl);

            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    
    public static class Converter {
    	// common/util に持って行っても良いけど、他で使うこともなさげなのでこのままで。
    	
    	public static byte[] toByte(final MultipartFile multipartFile) {
    		try {
    			return multipartFile.getBytes();
    		} catch (IOException e) {
    			return null;
    		}
    	}
    }

    public String getFileUrl(
            final String projectId,
            final String type,
            final String name
    ) {
        return UrlBuilder.url(config.api.baseUrl, "view/project", projectId, "file", type, name).build();
    }

    public String getFileNameForNextCloud(final String name) {
        // NextCloudに登録する様にスペースを変換する
        return name
                .replaceAll("\\s", "%20")
                .replace("　", "%E3%80%80");
    }

    public boolean validateFileType(
            final String type,
            final String name) {
        //  workbookでかつ.xlsx
        //  もしくはvcfでかつVCFでありえる拡張子だったらOK
        // FIXME 定数化
        var isWorkBook = type.equals("workbook") && name.matches(".*.xlsx");
        // .gzなどで圧縮している場合もありえるため、正規表現の末尾は*とした
        var isVCF      = type.equals("vcf") && name.matches(".*.vcf*");

        return isWorkBook || isVCF;
    }
}