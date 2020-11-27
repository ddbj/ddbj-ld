package ddbjld.api.app.core.module;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.common.utility.UrlBuilder;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
public class FileModule {

    @Autowired
    private ConfigSet config;

    private Sardine begin() {
        var admin    = config.nextcloud.client.ADMIN;
        var password = config.nextcloud.client.ADMIN_PASS;

        return SardineFactory.begin(admin, password);
    }

    public boolean exists(String... path) {
        var sardine = this.begin();
        var url     = UrlBuilder.url(config.nextcloud.endpoints.URL).path(path).build();

        try {
            return sardine.exists(url);
        } catch (IOException e) {
            log.debug(e.getMessage());

            return false;
        }
    }

    public List<DavResource> list(String... path) {
        var sardine = this.begin();
        var url     = UrlBuilder.url(config.nextcloud.endpoints.URL).path(path).build();

        try {
            return sardine.list(url);
        } catch (IOException e) {
            log.debug(e.getMessage());

            return null;
        }
    }

    public boolean createDirectory(String... path) {
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

    public boolean upload(byte[] file, String... path) {
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

    public byte[] download(String... path) {
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

    public boolean delete(String... path) {
        var sardine = this.begin();
        var url     = UrlBuilder.url(config.nextcloud.endpoints.URL).path(path).build();

        try {
            sardine.delete(url);

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean move(String path, String distPath) {
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

    public boolean copy(String path, String distPath) {
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

    public String getFileUrl(String projectId, String type, String name) {
        return UrlBuilder.url(config.api.baseUrl, "view/project", projectId, "file", type, name).build();
    }

    public String getFileNameForNextCloud(String name) {
        // NextCloudに登録する様にスペースを変換する
        return name
                .replaceAll("\\s", "%20")
                .replace("　", "%E3%80%80");
    }
}