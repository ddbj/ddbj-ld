package com.ddbj.ld.app.controller.v1.resource;

import com.ddbj.ld.app.core.module.SearchModule;
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

    // FIXME https://ddbj.nig.ac.jp/resource/ns/の開発する必要がある(内容を確認
    // FIXME context.jsonldの内容もアップデートする必要がある
    @GetMapping(value = "context.jsonld")
    public  String context() {

        return "{\n" +
                "\t\"@context\": [\n" +
                "\t\t\"https://schema.org/docs/jsonldcontext.json\",\n" +
                "\t\t{\n" +
                "\t\t\t\"@vocab\": \"https://ddbj.nig.ac.jp/resource/ns/\",\n" +
                "\t\t\t\"ddbj\": \"https://ddbj.nig.ac.jp/resource/ns/\",\n" +
                "\t\t\t\"identifier\": {\n" +
                "\t\t\t\t\"@id\": \"schema:identifier\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"name\": {\n" +
                "\t\t\t\t\"@id\": \"schema:name\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"title\": {\n" +
                "\t\t\t\t\"@id\": \"schema:title\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"description\": {\n" +
                "\t\t\t\t\"@id\": \"schema:description\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"dbXrefs\": {\n" +
                "\t\t\t\t\"@id\": \"ddbj:dbXrefs\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"type\": {\n" +
                "\t\t\t\t\"@id\": \"ddbj:type\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"properties\": {\n" +
                "\t\t\t\t\"@id\": \"ddbj:properties\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"center_name\": {\n" +
                "\t\t\t\t\"@id\": \"ddbj:center_name\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"DESCRIPTION\": {\n" +
                "\t\t\t\t\"@id\": \"ddbj:DESCRIPTION\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"TITLE\": {\n" +
                "\t\t\t\t\"@id\": \"ddbj:TITLE\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"accession\": {\n" +
                "\t\t\t\t\"@id\": \"ddbj:accession\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"POLICY_REF\": {\n" +
                "\t\t\t\t\"@id\": \"ddbj:POLICY_REF\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"refcenter\": {\n" +
                "\t\t\t\t\"@id\": \"ddbj:refcenter\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"refname\": {\n" +
                "\t\t\t\t\"@id\": \"ddbj:refname\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"dateCreated\": {\n" +
                "\t\t\t\t\"@id\": \"schema:dateCreated\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"dateModified\": {\n" +
                "\t\t\t\t\"@id\": \"schema:dateModified\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"datePublished\": {\n" +
                "\t\t\t\t\"@id\": \"schema:datePublished\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";
    }

    @GetMapping(value = "{type}/{identifier}.json")
    public  LinkedHashMap<String, Object> jsonExtension(
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {

        return this.searchModule.get(type, identifier);
    }

    @GetMapping(value = "{type}/{identifier}.jsonld")
    public  LinkedHashMap<String, Object> jsonLdExtension(
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {

        return this.searchModule.getJsonLd(type, identifier);
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
