package guru.springframework.recipeproject.services;

import guru.springframework.recipeproject.domain.Recipe;

public interface RecipeService {
    Iterable<Recipe> findAll();
}
