package com.ddbj.ld.app.controller.v1.resource;

import com.ddbj.ld.app.transact.service.SearchService;
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

    private SearchService searchService;

    // FIXME https://ddbj.nig.ac.jp/resource/ns/の開発する必要がある(内容を確認
    // FIXME context.jsonldの内容もアップデートする必要がある
    @GetMapping(value = "context.jsonld")
    public LinkedHashMap<String, Object> context() {
        return this.searchService.getContext();
    }

    @GetMapping(value = "{type}/{identifier}.json")
    public LinkedHashMap<String, Object> jsonExtension(
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {
        return this.searchService.getJsonData(type, identifier);
    }

    @GetMapping(value = "{type}/{identifier}.jsonld")
    public LinkedHashMap<String, Object> jsonLdExtension(
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {
        return this.searchService.getJsonldData(type, identifier);
    }

    // ヘッダにAccept: application/jsonがあった場合、jsonを返す
    @GetMapping(value = "{type}/{identifier}", headers = "Accept=application/json")
    public LinkedHashMap<String, Object> json(
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {
        return this.searchService.getJsonData(type, identifier);
    }

    // ヘッダにAccept: application/ld+jsonがあった場合、jsonldを返す
    @GetMapping(value = "{type}/{identifier}", headers = "Accept=application/ld+json")
    public LinkedHashMap<String, Object> ldJson(
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {
        return this.searchService.getJsonldData(type, identifier);
    }
}
