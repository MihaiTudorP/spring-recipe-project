package guru.springframework.recipeproject.services.implementations;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.converters.IngredientToIngredientCommand;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import guru.springframework.recipeproject.services.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static guru.springframework.recipeproject.services.implementations.RecipeServiceImpl.RECIPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    public static final String INGREDIENT_NOT_FOUND = "Ingredient not found!";
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(long recipeId, long ingredientId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException(RECIPE_NOT_FOUND));
        IngredientCommand ingredientCommand = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst().orElseThrow(() -> new RuntimeException(INGREDIENT_NOT_FOUND));
        return ingredientCommand;
    }
}
