package guru.springframework.recipeproject.converters;

import guru.springframework.recipeproject.commands.NotesCommand;
import guru.springframework.recipeproject.domain.Notes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotesCommandToNotesTest {
    public static final long ID = 1L;
    public static final String NOTES = "Test";
    @InjectMocks
    NotesCommandToNotes converter;

    @Test
    void convert(){
        assertNull(converter.convert(null));

        NotesCommand source = NotesCommand.builder().id(ID).recipeNotes(NOTES).build();
        Notes dest = converter.convert(source);
        assertEquals(ID, dest.getId());
        assertEquals(NOTES, dest.getRecipeNotes());
    }

}