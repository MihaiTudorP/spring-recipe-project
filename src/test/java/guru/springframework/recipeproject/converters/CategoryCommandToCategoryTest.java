package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.CategoryCommand;
import guru.springframework.recipeproject.domain.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryCommandToCategoryTest {

    @InjectMocks
    CategoryCommandToCategory converter;

    public static final String TEST_DESCRIPTION = "test description";
    public static final long ID = 1L;

    @Test
    void convert() {
        CategoryCommand source = CategoryCommand.builder().id(ID).description(TEST_DESCRIPTION).build();
        Category dest = converter.convert(source);
        assertNull(converter.convert(null));
        assertEquals(ID, dest.getId());
        assertEquals(TEST_DESCRIPTION, dest.getDescription());
    }
}