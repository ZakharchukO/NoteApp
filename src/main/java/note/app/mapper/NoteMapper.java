package note.app.mapper;

import note.app.domain.CreateUpdateNoteRequest;
import note.app.domain.Note;
import note.app.persistance.entity.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class NoteMapper {

  public abstract Note mapToNote(NoteEntity noteEntity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "likeNumber", expression = "java(createUpdateNoteRequest.isLike() ? 1 : 0)")
  public abstract NoteEntity mapToNoteEntity(CreateUpdateNoteRequest createUpdateNoteRequest);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "likeNumber", expression = "java(mapToLikeNumber(existingNoteEntity.getLikeNumber(), createUpdateNoteRequest.isLike()))")
  public abstract NoteEntity updateNoteEntity(@MappingTarget NoteEntity existingNoteEntity,
      CreateUpdateNoteRequest createUpdateNoteRequest);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "value", ignore = true)
  @Mapping(target = "likeNumber", expression = "java(mapToLikeNumber(existingNoteEntity.getLikeNumber(), like))")
  public abstract NoteEntity updateNoteEntity(@MappingTarget NoteEntity existingNoteEntity, Boolean like);

  protected Integer mapToLikeNumber(Integer likeNumber, boolean like) {
    return like ? ++likeNumber : --likeNumber;
  }
}
