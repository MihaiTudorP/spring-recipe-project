package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.RecipeCommand;
import guru.springframework.recipeproject.domain.Category;
import guru.springframework.recipeproject.domain.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;
    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null){
            return null;
        }
        Recipe dest = Recipe.builder()
                .id(source.getId())
                .description(source.getDescription())
                .cookTime(source.getCookTime())
                .prepTime(source.getPrepTime())
                .difficulty(source.getDifficulty())
                .directions(source.getDirections())
                .source(source.getSource())
                .servings(source.getServings())
                .url(source.getUrl())
                .notes(notesCommandToNotes.convert(source.getNotes()))
                .build();
        source.getCategories().forEach(categoryCommand -> {
            final Category category = categoryCommandToCategory.convert(categoryCommand);
            category.getRecipes().add(dest);
            dest.getCategories().add(category);
        });
        source.getIngredients().forEach(ingredientCommand -> {
            dest.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
        });
        return dest;
    }
}
