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

import java.util.Set;

import static guru.springframework.recipeproject.converters.RecipeCommandToRecipeTest.TEST_NOTES;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeToRecipeCommandTest {
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
    CategoryToCategoryCommand categoryToCategoryCommand;
    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;
    @Mock
    NotesToNotesCommand notesToNotesCommand;

    @InjectMocks
    RecipeToRecipeCommand converter;

    @Test
    void convert() {
        assertNull(converter.convert(null));

        final Category category1 = Category.builder().id(1L).build();
        final Category category2 = Category.builder().id(2L).build();
        final Set<Category> categories = Set.of(category1, category2);
        final Ingredient ingredient1 = Ingredient.builder().id(1L).build();
        final Ingredient ingredient2 = Ingredient.builder().id(2L).build();
        final Set<Ingredient> ingredients = Set.of(ingredient1, ingredient2);
        final Notes notes = Notes.builder().id(1L).recipeNotes(TEST_NOTES).build();
        Recipe source = Recipe.builder()
                .id(ID)
                .description(DESCRIPTION)
                .cookTime(MINUTES)
                .prepTime(MINUTES)
                .difficulty(DIFFICULTY)
                .directions(DIRECTIONS)
                .servings(SERVINGS)
                .source(SOURCE)
                .url(URL)
                .notes(notes)
                .categories(categories)
                .ingredients(ingredients).build();

        when(categoryToCategoryCommand.convert(category1)).thenReturn(CategoryCommand.builder().id(1L).build());
        when(categoryToCategoryCommand.convert(category2)).thenReturn(CategoryCommand.builder().id(2L).build());
        when(ingredientToIngredientCommand.convert(ingredient1)).thenReturn(IngredientCommand.builder().id(1L).build());
        when(ingredientToIngredientCommand.convert(ingredient2)).thenReturn(IngredientCommand.builder().id(2L).build());
        when(notesToNotesCommand.convert(notes)).thenReturn(NotesCommand.builder().id(1L).recipeNotes(TEST_NOTES).build());

        RecipeCommand dest = converter.convert(source);
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
    }
}