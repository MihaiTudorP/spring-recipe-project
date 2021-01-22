package guru.springframework.recipeproject.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand extends BaseCommand{
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasure;

    @Builder
    public IngredientCommand(Long id, String description, BigDecimal amount, UnitOfMeasureCommand unitOfMeasure) {
        super(id);
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }
}
