package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.services.IngredientService;
import guru.springframework.recipeproject.services.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @GetMapping("/recipe/{id}/ingredients")
    public String showIngredients(@PathVariable long id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "/recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable long recipeId,
                                       @PathVariable long ingredientId, Model model, HttpServletResponse response){
        try {
            model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
            return "recipe/ingredient/show";
        } catch (RuntimeException e){
            log.debug(String.format("Exception encountered: %s", e.getMessage()));
            return redirectNotFound(response, model, ingredientId, recipeId, e);
        }
    }

    private String redirectNotFound(HttpServletResponse response, Model model, Long id, Long recipeId, RuntimeException e) {
        model.addAttribute("exception", e.getMessage());
        model.addAttribute("ingredientId", id);
        model.addAttribute("recipeId", recipeId);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "/recipe/ingredient/notFound";
    }
}
