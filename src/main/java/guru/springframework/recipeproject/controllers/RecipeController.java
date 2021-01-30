package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            return redirectNotFound(response, model, id, e);
        }
    }

    private String redirectNotFound(HttpServletResponse response, Model model, Long id, RuntimeException e) {
        model.addAttribute("exception", e.getMessage());
        model.addAttribute("id", id);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "/recipe/notFound";
    }

    @GetMapping("/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "/recipe/recipeform";
    }

    @PostMapping(name = "recipe")
    public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand command){
       RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return String.format("redirect:/recipe/show/%d", savedCommand.getId());
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable long id, Model model, HttpServletResponse response){
        try {
            model.addAttribute("recipe", recipeService.findCommandById(id));
            return "/recipe/recipeform";
        } catch (RuntimeException e){
            return redirectNotFound(response, model, id, e);
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipeById(@PathVariable long id){
        recipeService.deleteById(id);
        return "redirect:/";
    }
}
