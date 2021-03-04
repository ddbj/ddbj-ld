package ddbjld.api.app.controller.v1.resource;

import ddbjld.api.app.core.module.SearchModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@Slf4j
@AllArgsConstructor
@RequestMapping({
        "v1/resource",
        "resource",
})
@RestController
public class ResourceController {

    private SearchModule searchModule;

    // extension(拡張子にあった結果を返す)
    @GetMapping(value = "{type}/{identifier}/{extension}")
    @Deprecated
    public String content(
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier,
            @PathVariable("extension") final String extension
    ) {

        return "";
    }

    // ヘッダにAccept: application/jsonがあった場合、jsonを返す
    @GetMapping(value = "{type}/{identifier}", headers = "Accept=application/json")
    public LinkedHashMap<String, Object> json(
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {
        return this.searchModule.get(type, identifier);
    }

    // ヘッダにAccept: application/ld+jsonがあった場合、jsonldを返す
    @GetMapping(value = "{type}/{identifier}", headers = "Accept=application/ld+json")
    public LinkedHashMap<String, Object> ldJson(
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {
        return this.searchModule.getJsonLd(type, identifier);
    }
}
