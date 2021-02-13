package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipeproject.domain.Ingredient;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.domain.UnitOfMeasure;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientCommandToIngredientTest {

    public static final String UOM = "uom";
    public static final String DESCRIPTION = "test";
    public static final long ID = 1L;
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(1);
    public static final long RECIPE_ID = 2L;
    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    @InjectMocks
    IngredientCommandToIngredient converter;

    UnitOfMeasureCommand unitOfMeasureCommand;
    UnitOfMeasure unitOfMeasure;

    @BeforeEach
    void setUp(){
        unitOfMeasureCommand = UnitOfMeasureCommand.builder().id(ID).description(UOM).build();
        unitOfMeasure = UnitOfMeasure.builder().id(ID).description(UOM).build();
    }

    @Test
    void convert() {
        when(uomConverter.convert(unitOfMeasureCommand)).thenReturn(unitOfMeasure);
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(Optional.of(Recipe.builder().id(RECIPE_ID).build()));
        assertNull(converter.convert(null));
        IngredientCommand source = IngredientCommand.builder()
                .id(ID)
                .recipeId(RECIPE_ID)
                .unitOfMeasure(unitOfMeasureCommand)
                .amount(AMOUNT)
                .description(DESCRIPTION).build();

        Ingredient ingredient = converter.convert(source);
        assertEquals(ID, ingredient.getId());
        assertEquals(RECIPE_ID, ingredient.getRecipe().getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertNotNull(ingredient.getUnitOfMeasure());
    }

    @Test
    void convertRecipeIdNull() {
        when(uomConverter.convert(unitOfMeasureCommand)).thenReturn(unitOfMeasure);
        assertNull(converter.convert(null));
        IngredientCommand source = IngredientCommand.builder()
                .id(ID)
                .recipeId(null)
                .unitOfMeasure(unitOfMeasureCommand)
                .amount(AMOUNT)
                .description(DESCRIPTION).build();

        Ingredient ingredient = converter.convert(source);
        assertEquals(ID, ingredient.getId());
        assertNull(ingredient.getRecipe());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertNotNull(ingredient.getUnitOfMeasure());
    }

    @Test
    void convertRecipeNotFound() {
        assertNull(converter.convert(null));
        IngredientCommand source = IngredientCommand.builder()
                .id(ID)
                .recipeId(RECIPE_ID)
                .unitOfMeasure(unitOfMeasureCommand)
                .amount(AMOUNT)
                .description(DESCRIPTION).build();

        assertThrows(RuntimeException.class, ()->converter.convert(source));
    }
}