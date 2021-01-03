package guru.springframework.recipeproject.services.implementations;

import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import guru.springframework.recipeproject.services.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    @Override
    public Iterable<Recipe> findAll() {
        log.debug("Fetching recipes");
        return recipeRepository.findAll();
    }
}
