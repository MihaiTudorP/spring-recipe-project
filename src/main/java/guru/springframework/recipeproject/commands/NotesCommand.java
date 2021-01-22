package guru.springframework.recipeproject.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand extends BaseCommand{
    private String recipeNotes;

    @Builder
    public NotesCommand(Long id, String recipeNotes) {
        super(id);
        this.recipeNotes = recipeNotes;
    }
}
