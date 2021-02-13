package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.domain.Ingredient;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static guru.springframework.recipeproject.services.implementations.RecipeServiceImpl.RECIPE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;
    private final RecipeRepository recipeRepository;
    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if(source == null) {
            return null;
        }
        Recipe recipe = null;
        if (source.getRecipeId() != null){
            recipe = recipeRepository.findById(source.getRecipeId()).orElseThrow(()-> new RuntimeException(RECIPE_NOT_FOUND));
        }

        return Ingredient.builder()
                .id(source.getId())
                .amount(source.getAmount())
                .recipe(recipe)
                .description(source.getDescription())
                .unitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()))
                .build();
    }
}
