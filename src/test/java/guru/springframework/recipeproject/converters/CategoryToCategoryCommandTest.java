package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.CategoryCommand;
import guru.springframework.recipeproject.domain.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryToCategoryCommandTest {

    public static final String DESCRIPTION = "test";
    public static final long ID = 1L;
    @InjectMocks
    CategoryToCategoryCommand converter;

    @Test
    void convert() {
        Category source = Category.builder().id(ID).description(DESCRIPTION).build();
        CategoryCommand categoryCommand = converter.convert(source);
        assertNull(converter.convert(null));
        assertEquals(ID, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }
}