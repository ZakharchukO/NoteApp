package note.app.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import note.app.domain.User;
import note.app.exception.ErrorCode;
import note.app.exception.NoteAppException;
import note.app.mapper.UserMapper;
import note.app.persistance.entity.UserEntity;
import note.app.persistance.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserMapper userMapper;
  private final UserRepository userRepository;

  @Transactional
  public void createUser(User user) {
    String email = user.getEmail();
    Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
    if (userEntityOptional.isPresent()) {
      log.info("Failed to create a new user with email '{}': already exists", email);
      throw NoteAppException.with(ErrorCode.USER_ALREADY_EXIST);
    }

    UserEntity userEntity = userMapper.mapToUserEntity(user);
    userRepository.save(userEntity);
  }

  @Transactional(readOnly = true)
  public User getUser(String email) {
    Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
    if (userEntityOptional.isEmpty()) {
      log.info("Failed to get user with email '{}': entity not found", email);
      throw NoteAppException.with(ErrorCode.USER_NOT_FOUND);
    }

    UserEntity userEntity = userEntityOptional.get();
    return userMapper.mapToUser(userEntity);
  }
}
