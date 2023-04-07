
package note.app.persistance.repository;

import java.util.List;
import note.app.persistance.entity.NoteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<NoteEntity, Long> {

  @Override
  @Query("select n from NoteEntity n "
      + "order by n.createdDate desc")
  List<NoteEntity> findAll();
}
