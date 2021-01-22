package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.CategoryCommand;
import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.commands.NotesCommand;
import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeCommandToRecipeTest {

    public static final long ID = 1L;
    public static final String DESCRIPTION = "test";
    public static final int MINUTES = 1;
    public static final String DIRECTIONS = "test directions";
    public static final int SERVINGS = 1;
    public static final String SOURCE = "test source";
    public static final String URL = "http://www.test.com";
    public static final Difficulty DIFFICULTY = Difficulty.HARD;
    public static final String TEST_NOTES = "test notes";
    @Mock
    CategoryCommandToCategory categoryCommandToCategory;
    @Mock
    IngredientCommandToIngredient ingredientCommandToIngredient;
    @Mock
    NotesCommandToNotes notesCommandToNotes;

    @InjectMocks
    RecipeCommandToRecipe converter;

    @Test
    void convert() {
        assertNull(converter.convert(null));

        final CategoryCommand categoryCommand1 = CategoryCommand.builder().id(1L).build();
        final CategoryCommand categoryCommand2 = CategoryCommand.builder().id(2L).build();
        final Set<CategoryCommand> categoryCommands = Set.of(categoryCommand1, categoryCommand2);
        final IngredientCommand ingredientCommand1 = IngredientCommand.builder().id(1L).build();
        final IngredientCommand ingredientCommand2 = IngredientCommand.builder().id(2L).build();
        final Set<IngredientCommand> ingredientCommands = Set.of(ingredientCommand1, ingredientCommand2);
        final NotesCommand notesCommand = NotesCommand.builder().id(1L).recipeNotes(TEST_NOTES).build();
        RecipeCommand source = RecipeCommand.builder()
                .id(ID)
                .description(DESCRIPTION)
                .cookTime(MINUTES)
                .prepTime(MINUTES)
                .difficulty(DIFFICULTY)
                .directions(DIRECTIONS)
                .servings(SERVINGS)
                .source(SOURCE)
                .url(URL)
                .notes(notesCommand)
                .categories(categoryCommands)
                .ingredients(ingredientCommands).build();

        when(categoryCommandToCategory.convert(categoryCommand1)).thenReturn(Category.builder().id(1L).build());
        when(categoryCommandToCategory.convert(categoryCommand2)).thenReturn(Category.builder().id(2L).build());
        when(ingredientCommandToIngredient.convert(ingredientCommand1)).thenReturn(Ingredient.builder().id(1L).build());
        when(ingredientCommandToIngredient.convert(ingredientCommand2)).thenReturn(Ingredient.builder().id(2L).build());
        when(notesCommandToNotes.convert(notesCommand)).thenReturn(Notes.builder().id(1L).recipeNotes(TEST_NOTES).build());

        Recipe dest = converter.convert(source);
        assertEquals(ID, dest.getId());
        assertEquals(DESCRIPTION, dest.getDescription());
        assertEquals(MINUTES, dest.getCookTime());
        assertEquals(MINUTES, dest.getPrepTime());
        assertEquals(DIFFICULTY, dest.getDifficulty());
        assertEquals(DIRECTIONS, dest.getDirections());
        assertEquals(SERVINGS, dest.getServings());
        assertEquals(SOURCE, dest.getSource());
        assertEquals(URL, dest.getUrl());
        assertNotNull(dest.getNotes());
        assertEquals(2, dest.getCategories().size());
        assertEquals(2, dest.getIngredients().size());
        dest.getCategories().forEach(category -> assertTrue(
                category.getRecipes().stream().anyMatch(recipe -> isSameRecipe(dest, recipe))));
        dest.getIngredients().forEach(ingredient -> assertSame(dest, ingredient.getRecipe()));
    }

    private boolean isSameRecipe(Recipe dest, Recipe recipe) {
        return recipe.getId().equals(dest.getId());
    }
}