package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/show/{id}")
    public String showRecipe(HttpServletResponse response, Model model, @PathVariable Long id){
        try {
            model.addAttribute("recipe", recipeService.findById(id));
            return "/recipe/show";
        } catch (RuntimeException e){
            model.addAttribute("exception", e.getMessage());
            model.addAttribute("id", id);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "/recipe/notFound";
        }
    }
}
