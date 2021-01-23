package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static guru.springframework.recipeproject.services.implementations.RecipeServiceImpl.RECIPE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    public static final String URL = "/recipe/show/1";
    @Mock
    RecipeService recipeService;
    @InjectMocks
    RecipeController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void showRecipe() throws Exception {
        when(recipeService.findById(1L)).thenReturn(Recipe.builder().id(1L).description("test").build());
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    void showRecipeNotFound() throws Exception{
        when(recipeService.findById(1L)).thenThrow(new RuntimeException(RECIPE_NOT_FOUND));
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("/recipe/notFound"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("id"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("exception"));
    }

    @Test
    void newRecipe() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }
}