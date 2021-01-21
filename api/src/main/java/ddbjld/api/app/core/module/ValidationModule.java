package ddbjld.api.app.core.module;

import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.app.feasibility.common.annotation.Module;
import ddbjld.api.common.utility.JsonMapper;
import ddbjld.api.common.utility.api.StandardRestClient;
import ddbjld.api.data.beans.ValidationInfo;
import ddbjld.api.data.beans.ValidationResult;
import ddbjld.api.data.beans.ValidationStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Module
@AllArgsConstructor
@Slf4j
public class ValidationModule {
    private ConfigSet config;

    public ValidationInfo validate(FileSystemResource file) {
        // FIXME 例外処理がでないようにする、他と同じようにRestApiを使えるようにする

        final String url = config.validation.endpoints.VALIDATE;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("jvar", file);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<ValidationInfo> response = restTemplate
                    .postForEntity(url, requestEntity, ValidationInfo.class);

            return response.getBody();
        } catch (HttpStatusCodeException e) {
            // FIXME エラーハンドリング
            log.error(e.getMessage());
            return null;
        }
    }

    public ValidationResult getValidationResult(UUID uuid) {
        final String url = config.validation.endpoints.RESULT.replace("{uuid}", uuid.toString());

        var client = new StandardRestClient();
        var api    = client.get(url);

        if(api.response.not2xxSuccessful()) {
            return null;
        }

        return JsonMapper.parse(api.response.body, ValidationResult.class);
    }

    public ValidationStatus getValidationStatus(UUID uuid) {
        final String url = config.validation.endpoints.STATUS.replace("{uuid}", uuid.toString());

        var client = new StandardRestClient();
        var api    = client.get(url);

        if(api.response.not2xxSuccessful()) {
            return null;
        }

        return JsonMapper.parse(api.response.body, ValidationStatus.class);
    }

    public String getMetadataJson(UUID uuid) {
        final String url = config.validation.endpoints.METADATA_JSON.replace("{uuid}", uuid.toString());

        var client = new StandardRestClient();
        var api    = client.get(url);

        if(api.response.not2xxSuccessful()) {
            return null;
        }

        return api.response.body;
    }
}
