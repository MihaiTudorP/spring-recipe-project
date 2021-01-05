package guru.springframework.recipeproject.repositories;

import guru.springframework.recipeproject.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryIT {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void findByDescription() {
        final String description = "American";
        Category category = categoryRepository.findByDescription(description).orElse(null);
        assertEquals(description, category.getDescription());
    }
}