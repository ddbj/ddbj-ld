package ddbjld.api.app.feasibility.controller;

import ddbjld.api.app.core.module.SearchModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping({
        "v1/resource",
        "resource",
})
@Controller
public class ResourceViewController {

    @Autowired
    private SearchModule searchModule;

    // FIXME json,json-ld、拡張子指定と同じメソッドとする
    // ヘッダにAccept: application/htmlがあった場合、htmlを返す
    @GetMapping(value = "{type}/{identifier}", headers = "Accept=application/html")
    public String html(
            Model model,
            final HttpServletRequest request,
            final HttpServletResponse response,
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {
        if(identifier.equals("undefined")) {
            // FIXME 暫定対応：ヘッダの投げてくるリクエストを弾く
            return null;
        }

        var item = this.searchModule.get(type, identifier);

        model.addAttribute("item", item);

        return "resource";
    }
}
