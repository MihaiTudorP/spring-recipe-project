package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.NotesCommand;
import guru.springframework.recipeproject.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {
    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if (source == null) {
            return null;
        }
        return Notes.builder().id(source.getId()).recipeNotes(source.getRecipeNotes()).build();
    }
}
