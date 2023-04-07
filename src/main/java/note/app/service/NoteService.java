package note.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import note.app.domain.CreateUpdateNoteRequest;
import note.app.domain.Note;
import note.app.exception.ErrorCode;
import note.app.exception.NoteAppException;
import note.app.mapper.NoteMapper;
import note.app.persistance.entity.NoteEntity;
import note.app.persistance.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {

  private final NoteMapper noteMapper;
  private final NoteRepository noteRepository;

  @Transactional(readOnly = true)
  public Note getNote(Long noteId) {
    NoteEntity noteEntity = getNoteEntity(noteId);
    return noteMapper.mapToNote(noteEntity);
  }

  @Transactional(readOnly = true)
  public List<Note> getAllNotes() {
    return noteRepository.findAll().stream()
        .map(noteMapper::mapToNote)
        .collect(Collectors.toList());
  }

  public Long createNote(CreateUpdateNoteRequest createUpdateNoteRequest) {
    NoteEntity noteEntity = noteMapper.mapToNoteEntity(createUpdateNoteRequest);
    return noteRepository.save(noteEntity).getId();
  }

  public void updateNote(Long noteId, CreateUpdateNoteRequest createUpdateNoteRequest) {
    NoteEntity noteEntity = getNoteEntity(noteId);
    NoteEntity updatedNoteEntity = noteMapper.updateNoteEntity(noteEntity, createUpdateNoteRequest);
    noteRepository.save(updatedNoteEntity);
  }

  public void deleteNote(Long noteId) {
    NoteEntity noteEntity = getNoteEntity(noteId);
    noteRepository.delete(noteEntity);
  }

  public void updateNote(Long noteId, boolean like) {
    NoteEntity noteEntity = getNoteEntity(noteId);
    NoteEntity updatedNoteEntity = noteMapper.updateNoteEntity(noteEntity, like);
    noteRepository.save(updatedNoteEntity);
  }

  private NoteEntity getNoteEntity(Long noteId) {
    Optional<NoteEntity> noteEntityOptional = noteRepository.findById(noteId);
    if (noteEntityOptional.isEmpty()) {
      log.info("Failed to get note by ID '{}': entity not found", noteId);
      throw NoteAppException.with(ErrorCode.NOTE_NOT_FOUND);
    }

    return noteEntityOptional.get();
  }
}
