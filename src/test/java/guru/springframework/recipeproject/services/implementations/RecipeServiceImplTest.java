package guru.springframework.recipeproject.services.implementations;

import guru.springframework.recipeproject.commands.CategoryCommand;
import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.commands.NotesCommand;
import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.converters.RecipeCommandToRecipe;
import guru.springframework.recipeproject.converters.RecipeToRecipeCommand;
import guru.springframework.recipeproject.domain.*;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    public static final long ID = 1L;
    public static final String DESCRIPTION = "test";
    public static final int MINUTES = 1;
    public static final String DIRECTIONS = "test directions";
    public static final int SERVINGS = 1;
    public static final String SOURCE = "test source";
    public static final String URL = "http://www.test.com";
    public static final Difficulty DIFFICULTY = Difficulty.HARD;
    public static final String TEST_NOTES = "test notes";

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    void findAll() {
        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.findAll();
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void findById(){
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(Recipe.builder().id(1L).description(DESCRIPTION).build()));
        Recipe recipe = recipeService.findById(1L);
        assertEquals(1L, (long) recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        verify(recipeRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound(){
        assertThrows(RuntimeException.class, () -> recipeService.findById(1L));
        verify(recipeRepository, times(1)).findById(1L);
    }

    @Test
    void saveRecipeCommand(){
        final CategoryCommand categoryCommand1 = CategoryCommand.builder().id(1L).build();
        final CategoryCommand categoryCommand2 = CategoryCommand.builder().id(2L).build();
        final Set<CategoryCommand> categoryCommands = Set.of(categoryCommand1, categoryCommand2);
        final IngredientCommand ingredientCommand1 = IngredientCommand.builder().id(1L).build();
        final IngredientCommand ingredientCommand2 = IngredientCommand.builder().id(2L).build();
        final Set<IngredientCommand> ingredientCommands = Set.of(ingredientCommand1, ingredientCommand2);
        final NotesCommand notesCommand = NotesCommand.builder().id(1L).recipeNotes(TEST_NOTES).build();
        RecipeCommand command = RecipeCommand.builder()
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

        final Category category1 = Category.builder().id(1L).build();
        final Category category2 = Category.builder().id(2L).build();
        final Set<Category> categories = Set.of(category1, category2);
        final Ingredient ingredient1 = Ingredient.builder().id(1L).build();
        final Ingredient ingredient2 = Ingredient.builder().id(2L).build();
        final Set<Ingredient> ingredients = Set.of(ingredient1, ingredient2);
        final Notes notes = Notes.builder().id(1L).recipeNotes(TEST_NOTES).build();
        Recipe convertedRecipe = Recipe.builder()
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

        Recipe savedRecipe = Recipe.builder()
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

        RecipeCommand savedCommand = RecipeCommand.builder()
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

        when(recipeCommandToRecipe.convert(command)).thenReturn(convertedRecipe);
        when(recipeRepository.save(convertedRecipe)).thenReturn(savedRecipe);
        when(recipeToRecipeCommand.convert(savedRecipe)).thenReturn(savedCommand);

        RecipeCommand output = recipeService.saveRecipeCommand(command);
        assertNotNull(output);
        assertEquals(ID, (long) output.getId());
        verify(recipeCommandToRecipe, times(1)).convert(command);
        verify(recipeRepository, times(1)).save(convertedRecipe);
        verify(recipeToRecipeCommand, times(1)).convert(savedRecipe);
    }

    @Test
    void testDeleteById(){
        Long idToDelete = 2L;
        recipeService.deleteById(idToDelete);
        verify(recipeRepository, times(1)).deleteById(2L);
    }

    @Test
    void testFindCommandById(){
        final Recipe recipe = Recipe.builder().id(1L).build();
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeToRecipeCommand.convert(recipe)).thenReturn(RecipeCommand.builder().id(1L).build());
        final RecipeCommand command = recipeService.findCommandById(1L);
        assertNotNull(command);
        assertEquals(1L, (long)command.getId());
        verify(recipeRepository, times(1)).findById(1L);
        verify(recipeToRecipeCommand, times(1)).convert(recipe);
    }

    @Test
    void testFindCommandByIdNotFound(){
        assertThrows(RuntimeException.class, ()->recipeService.findCommandById(1L));
        verify(recipeRepository, times(1)).findById(1L);
        verify(recipeToRecipeCommand, times(0)).convert(any());
    }
}