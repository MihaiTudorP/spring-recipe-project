package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.CategoryCommand;
import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.domain.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final NotesToNotesCommand notesToNotesCommand;
    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }
        RecipeCommand dest = RecipeCommand.builder()
                .id(source.getId())
                .description(source.getDescription())
                .cookTime(source.getCookTime())
                .prepTime(source.getPrepTime())
                .difficulty(source.getDifficulty())
                .directions(source.getDirections())
                .source(source.getSource())
                .servings(source.getServings())
                .url(source.getUrl())
                .notes(notesToNotesCommand.convert(source.getNotes()))
                .build();
        source.getCategories().forEach(category -> {
            final CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);
            dest.getCategories().add(categoryCommand);
        });
        source.getIngredients().forEach(ingredient -> {
            dest.getIngredients().add(ingredientToIngredientCommand.convert(ingredient));
        });
        return dest;
    }
}
