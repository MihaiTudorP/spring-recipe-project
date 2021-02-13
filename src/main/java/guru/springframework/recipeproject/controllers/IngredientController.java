package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class IngredientController {

    private final RecipeService recipeService;

    @GetMapping("/recipe/{id}/ingredients")
    public String showIngredients(@PathVariable long id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "/recipe/ingredient/list";
    }
}
