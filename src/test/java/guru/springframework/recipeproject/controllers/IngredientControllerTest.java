package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.services.IngredientService;
import guru.springframework.recipeproject.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static guru.springframework.recipeproject.services.implementations.RecipeServiceImpl.RECIPE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    public static final String URL_RECIPE_INGREDIENT_SHOW = "/recipe/1/ingredient/2/show";
    @InjectMocks
    IngredientController controller;

    @Mock
    IngredientService ingredientService;

    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListIngredients() throws Exception{
        RecipeCommand recipeCommand = RecipeCommand.builder().id(1L).build();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(1L);
    }

    @Test
    void testShowIngredient() throws Exception{
        IngredientCommand ingredientCommand = IngredientCommand.builder().build();

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_RECIPE_INGREDIENT_SHOW))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    void showIngredientResourceNotFound() throws Exception{
        when(ingredientService.findByRecipeIdAndIngredientId(1L, 2L)).thenThrow(new RuntimeException("test"));
        mockMvc.perform(MockMvcRequestBuilders.get(URL_RECIPE_INGREDIENT_SHOW))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("/recipe/ingredient/notFound"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredientId", "recipeId"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("exception"));
    }
}