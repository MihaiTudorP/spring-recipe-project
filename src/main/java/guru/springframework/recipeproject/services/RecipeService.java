package guru.springframework.recipeproject.services;

import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.domain.Recipe;

import javax.transaction.Transactional;
import java.util.Set;

public interface RecipeService {
    Set<Recipe> findAll();

    Recipe findById(long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    void deleteById(Long idToDelete);

    RecipeCommand findCommandById(long id);
}
