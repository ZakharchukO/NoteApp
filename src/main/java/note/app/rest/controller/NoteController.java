package note.app.rest.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import note.app.domain.CreateUpdateNoteRequest;
import note.app.domain.Note;
import note.app.rest.api.generated.NoteApi;
import note.app.rest.domain.generated.CreateUpdateNoteRequestTO;
import note.app.rest.domain.generated.NoteTO;
import note.app.rest.domain.generated.UpdateLikeRequestTO;
import note.app.rest.mapper.NoteTOMapper;
import note.app.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoteController implements NoteApi {

  private final NoteService noteService;
  private final NoteTOMapper noteTOMapper;

  @Override
  public ResponseEntity<NoteTO> getNote(Long noteId) {
    Note note = noteService.getNote(noteId);
    NoteTO noteTO = noteTOMapper.mapToNoteTO(note);
    return ResponseEntity.ok(noteTO);
  }

  @Override
  public ResponseEntity<List<NoteTO>> getAllNotes() {
    List<Note> noteList = noteService.getAllNotes();
    List<NoteTO> noteTOList = noteTOMapper.mapToNoteTOList(noteList);
    return ResponseEntity.ok(noteTOList);
  }

  @Override
  public ResponseEntity<Long> createNote(CreateUpdateNoteRequestTO body) {
    CreateUpdateNoteRequest createUpdateNoteRequest = noteTOMapper.mapToCreateUpdateNoteRequest(
        body);
    Long noteId = noteService.createNote(createUpdateNoteRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(noteId);
  }

  @Override
  public ResponseEntity<Void> updateNote(Long noteId, CreateUpdateNoteRequestTO body) {
    CreateUpdateNoteRequest createUpdateNoteRequest = noteTOMapper.mapToCreateUpdateNoteRequest(
        body);
    noteService.updateNote(noteId, createUpdateNoteRequest);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> deleteNote(Long noteId) {
    noteService.deleteNote(noteId);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> updateLike(Long noteId, UpdateLikeRequestTO body) {
    noteService.updateNote(noteId, body.isLike());
    return ResponseEntity.noContent().build();
  }
}
