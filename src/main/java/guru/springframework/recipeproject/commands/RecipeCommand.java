package guru.springframework.recipeproject.commands;

import guru.springframework.recipeproject.domain.Difficulty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand extends BaseCommand{
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();

    @Builder
    public RecipeCommand(Long id, String description, Integer prepTime, Integer cookTime, Integer servings, String source, String url, String directions, Set<IngredientCommand> ingredients, Difficulty difficulty, NotesCommand notes, Set<CategoryCommand> categories) {
        super(id);
        this.description = description;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.source = source;
        this.url = url;
        this.directions = directions;
        if (ingredients != null) {
            this.ingredients = ingredients;
        }
        this.difficulty = difficulty;
        this.notes = notes;
        if (categories != null) {
            this.categories = categories;
        }
    }
}
