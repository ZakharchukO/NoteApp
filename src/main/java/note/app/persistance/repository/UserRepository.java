package note.app.persistance.repository;

import java.util.Optional;
import note.app.persistance.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);
}
