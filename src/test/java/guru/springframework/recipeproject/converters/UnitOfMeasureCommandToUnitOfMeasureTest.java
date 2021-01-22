package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipeproject.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureCommandToUnitOfMeasureTest {
    public static final String DESCRIPTION = "Test";
    public static final long ID = 1L;
    @InjectMocks
    UnitOfMeasureCommandToUnitOfMeasure converter;

    @Test
    void convert() {
        UnitOfMeasureCommand source = UnitOfMeasureCommand.builder().id(ID).description(DESCRIPTION).build();
        UnitOfMeasure dest = converter.convert(source);
        assertNull(converter.convert(null));
        assertEquals(ID, dest.getId());
        assertEquals(DESCRIPTION, dest.getDescription());
    }
}