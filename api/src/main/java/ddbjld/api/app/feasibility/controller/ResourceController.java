package ddbjld.api.app.feasibility.controller;

import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.app.core.module.ElasticSearchModule;
import ddbjld.api.common.utility.UrlBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@Slf4j
@RequestMapping({
        "v1/resource",
        "resource",
})
@RestController
public class ResourceController {

    @Autowired
    private ElasticSearchModule elasticSearchModule;

    @Autowired
    private ConfigSet configSet;

    // extension(拡張子にあった結果を返す)
    @GetMapping(value = "{type}/{identifier}/{extension}")
    public String content(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier,
            @PathVariable("extension") final String extension
    ) {

        return "";
    }

    // ヘッダにAccept: application/jsonがあった場合、jsonを返す
    @GetMapping(value = "{type}/{identifier}", headers = "Accept=application/json")
    public LinkedHashMap<String, Object> json(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {
        return this.elasticSearchModule.get(type, identifier);
    }

    // ヘッダにAccept: application/ld+jsonがあった場合、jsonldを返す
    @GetMapping(value = "{type}/{identifier}", headers = "Accept=application/ld+json")
    public LinkedHashMap<String, Object> ldJson(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {

        return this.elasticSearchModule.getJsonLd(type, identifier);
    }

    // ヘッダにAccept: application/htmlがあった場合、htmlを返す
    @GetMapping(value = "{type}/{identifier}", headers = "Accept=application/html")
    public String html(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {

        return "resource";
    }
}
