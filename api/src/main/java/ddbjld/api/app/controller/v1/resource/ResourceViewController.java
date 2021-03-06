package ddbjld.api.app.controller.v1.resource;

import ddbjld.api.app.core.module.SearchModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@AllArgsConstructor
@RequestMapping({
        "v1/resource",
        "resource",
})
@Controller
public class ResourceViewController {

    private SearchModule searchModule;

    // ヘッダにAccept: application/htmlがあった場合、htmlを返す
    @GetMapping(value = "{type}/{identifier}", headers = "Accept=application/html")
    public String html(
            Model model,
            @PathVariable("type") final String type,
            @PathVariable("identifier") final String identifier
    ) {
        var item = this.searchModule.get(type, identifier);

        model.addAttribute("item", item);

        return "resource";
    }
}
