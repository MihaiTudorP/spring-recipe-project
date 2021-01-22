package guru.springframework.recipeproject.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<>();

    @Builder
    public Category(Long id, String description, Set<Recipe> recipes) {
        this.id = id;
        this.description = description;
        if (recipes != null) {
            this.recipes = recipes;
            recipes.forEach(recipe -> recipe.getCategories().add(this));
        }
    }
}
