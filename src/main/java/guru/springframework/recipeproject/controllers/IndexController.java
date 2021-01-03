package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.services.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final RecipeService recipeService;

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){
        log.debug("Showing the recipes...");
        model.addAttribute("recipes", recipeService.findAll());
        return "index";
    }
}
