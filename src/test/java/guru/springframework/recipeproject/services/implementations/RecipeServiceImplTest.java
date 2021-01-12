package guru.springframework.recipeproject.services.implementations;

import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    public static final String DESCRIPTION = "test";
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void findAll() {
        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.findAll();
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void findById(){
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(Recipe.builder().id(1L).description(DESCRIPTION).build()));
        Recipe recipe = recipeService.findById(1L);
        assertEquals(1L, (long) recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        verify(recipeRepository, times(1)).findById(1L);
    }

    @Test(expected = RuntimeException.class)
    public void findByIdNotFound(){
        assertNull(recipeService.findById(1L));
        verify(recipeRepository, times(1)).findById(1L);
    }
}