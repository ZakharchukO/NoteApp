package note.app.rest.mapper;

import java.util.List;
import note.app.domain.CreateUpdateNoteRequest;
import note.app.domain.Note;
import note.app.rest.domain.generated.CreateUpdateNoteRequestTO;
import note.app.rest.domain.generated.NoteTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteTOMapper {

  NoteTO mapToNoteTO(Note note);

  List<NoteTO> mapToNoteTOList(List<Note> noteList);

  CreateUpdateNoteRequest mapToCreateUpdateNoteRequest(
      CreateUpdateNoteRequestTO createUpdateNoteRequestTO);
}
