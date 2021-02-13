package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static guru.springframework.recipeproject.services.implementations.RecipeServiceImpl.RECIPE_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    public static final String URL_SHOW = "/recipe/1/show";
    public static final String URL_UPDATE = "/recipe/1/update";
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
        mockMvc.perform(MockMvcRequestBuilders.get(URL_SHOW))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    void showRecipeNotFound() throws Exception{
        when(recipeService.findById(1L)).thenThrow(new RuntimeException(RECIPE_NOT_FOUND));
        mockMvc.perform(MockMvcRequestBuilders.get(URL_SHOW))
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

    @Test
    void saveOrUpdateRecipe() throws Exception{
        RecipeCommand savedRecipeCommand = RecipeCommand.builder()
                .id(1L)
                .description("test")
                .prepTime(1)
                .cookTime(1)
                .servings(1)
                .source("test")
                .build();
        when(recipeService.saveRecipeCommand(any())).thenReturn(savedRecipeCommand);
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .content(buildUrlEncodedFormEntity(
                    "id", "",
                    "description", "test",
                    "prepTime", "1",
                    "cookTime", "1",
                    "servings", "1",
                    "source", "test")))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/recipe/1/show"));
    }

    @Test
    void testDeleteAction() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        verify(recipeService, times(1)).deleteById(1L);
    }

    @Test
    void updateRecipeNotFound() throws Exception{
        when(recipeService.findCommandById(1L)).thenThrow(new RuntimeException(RECIPE_NOT_FOUND));
        mockMvc.perform(MockMvcRequestBuilders.get(URL_UPDATE))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("/recipe/notFound"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("id"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("exception"));
    }

    @Test
    void updateRecipe() throws Exception {
        when(recipeService.findCommandById(1L)).thenReturn(RecipeCommand.builder().id(1L).description("test").build());
        mockMvc.perform(MockMvcRequestBuilders.get(URL_UPDATE))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    private String buildUrlEncodedFormEntity(String... params) {
        if( (params.length % 2) > 0 ) {
            throw new IllegalArgumentException("Need to give an even number of parameters");
        }
        StringBuilder result = new StringBuilder();
        for (int i=0; i<params.length; i+=2) {
            if( i > 0 ) {
                result.append('&');
            }
            try {
                result.
                        append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name())).
                        append('=').
                        append(URLEncoder.encode(params[i+1], StandardCharsets.UTF_8.name()));
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toString();
    }
}