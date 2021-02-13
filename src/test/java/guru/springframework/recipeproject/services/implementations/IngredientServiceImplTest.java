package guru.springframework.recipeproject.services.implementations;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.converters.IngredientToIngredientCommand;
import guru.springframework.recipeproject.domain.Ingredient;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {
    public static final long RECIPE_ID = 1L;
    public static final long INGREDIENT_ID = 2L;
    @InjectMocks
    IngredientServiceImpl ingredientService;

    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    RecipeRepository recipeRepository;

    @Test
    void findByRecipeIdAndIngredientId() {
        Recipe recipe = Recipe.builder().id(RECIPE_ID).build();
        Ingredient ingredient = Ingredient.builder().id(INGREDIENT_ID).build();
        recipe.addIngredient(ingredient);
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(Optional.of(recipe));
        when(ingredientToIngredientCommand.convert(ingredient)).thenReturn(IngredientCommand.builder().id(INGREDIENT_ID).recipeId(RECIPE_ID).build());
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID);
        assertEquals(INGREDIENT_ID, ingredientCommand.getId());
        assertEquals(RECIPE_ID, ingredientCommand.getRecipeId());
    }

    @Test
    void findByRecipeIdAndIngredientIdRecipeNotFound(){
        assertThrows(RuntimeException.class, ()->ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID));
    }

    @Test
    void findByRecipeIdAndIngredientIdIngredientNotFound(){
        Recipe recipe = Recipe.builder().id(RECIPE_ID).build();
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(Optional.of(recipe));
        assertThrows(RuntimeException.class, ()->ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID));
    }


}