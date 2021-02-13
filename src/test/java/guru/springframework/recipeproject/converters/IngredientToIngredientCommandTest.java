package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipeproject.domain.Ingredient;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientToIngredientCommandTest {

    public static final String UOM = "uom";
    public static final String DESCRIPTION = "test";
    public static final long ID = 1L;
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(1);
    public static final long RECIPE_ID = 2L;
    @Mock
    UnitOfMeasureToUnitOfMeasureCommand uomConverter;
    @InjectMocks
    IngredientToIngredientCommand converter;

    UnitOfMeasureCommand unitOfMeasureCommand;
    UnitOfMeasure unitOfMeasure;

    @BeforeEach
    void setUp(){
        unitOfMeasureCommand = UnitOfMeasureCommand.builder().id(ID).description(UOM).build();
        unitOfMeasure = UnitOfMeasure.builder().id(ID).description(UOM).build();
        when(uomConverter.convert(unitOfMeasure)).thenReturn(unitOfMeasureCommand);
    }

    @Test
    void convert() {
        assertNull(converter.convert(null));
        Ingredient source = Ingredient.builder()
                .id(ID)
                .unitOfMeasure(unitOfMeasure)
                .recipe(Recipe.builder().id(RECIPE_ID).build())
                .amount(AMOUNT)
                .description(DESCRIPTION).build();

        IngredientCommand dest = converter.convert(source);
        assertEquals(ID, dest.getId());
        assertEquals(AMOUNT, dest.getAmount());
        assertEquals(RECIPE_ID, dest.getRecipeId());
        assertEquals(DESCRIPTION, dest.getDescription());
        assertNotNull(dest.getUnitOfMeasure());
    }

    @Test
    void convertRecipeNull() {
        Ingredient source = Ingredient.builder()
                .id(ID)
                .unitOfMeasure(unitOfMeasure)
                .amount(AMOUNT)
                .description(DESCRIPTION).build();

        IngredientCommand dest = converter.convert(source);
        assertEquals(ID, dest.getId());
        assertEquals(AMOUNT, dest.getAmount());
        assertNull(dest.getRecipeId());
        assertEquals(DESCRIPTION, dest.getDescription());
        assertNotNull(dest.getUnitOfMeasure());
    }
}