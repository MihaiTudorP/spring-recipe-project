package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipeproject.domain.Ingredient;
import guru.springframework.recipeproject.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientCommandToIngredientTest {

    public static final String UOM = "uom";
    public static final String DESCRIPTION = "test";
    public static final long ID = 1L;
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(1);
    @Mock
    UnitOfMeasureCommandToUnitOfMeasure uomConverter;
    @InjectMocks
    IngredientCommandToIngredient converter;

    @Test
    void convert() {
        assertNull(converter.convert(null));

        UnitOfMeasureCommand unitOfMeasureCommand = UnitOfMeasureCommand.builder().id(ID).description(UOM).build();
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder().id(ID).description(UOM).build();
        when(uomConverter.convert(unitOfMeasureCommand)).thenReturn(unitOfMeasure);
        IngredientCommand source = IngredientCommand.builder()
                .id(ID)
                .unitOfMeasure(unitOfMeasureCommand)
                .amount(AMOUNT)
                .description(DESCRIPTION).build();

        Ingredient ingredient = converter.convert(source);
        assertEquals(ID, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertNotNull(ingredient.getUnitOfMeasure());
    }
}