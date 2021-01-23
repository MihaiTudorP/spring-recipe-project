package guru.springframework.recipeproject.services.implementations;

import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.converters.RecipeCommandToRecipe;
import guru.springframework.recipeproject.converters.RecipeToRecipeCommand;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import guru.springframework.recipeproject.services.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    public static final String RECIPE_NOT_FOUND = "Recipe not found!";
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public Set<Recipe> findAll() {
        log.debug("Fetching recipes");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException(RECIPE_NOT_FOUND)) ;
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug(String.format("Saved recipe id: %d", savedRecipe.getId()));
        return recipeToRecipeCommand.convert(savedRecipe);
    }
}
