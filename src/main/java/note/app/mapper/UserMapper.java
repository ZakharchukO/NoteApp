package note.app.mapper;

import note.app.domain.User;
import note.app.persistance.entity.UserEntity;
import note.app.security.PasswordEncoder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

  protected PasswordEncoder passwordEncoder;

  @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
  public abstract UserEntity mapToUserEntity(User user);

  public abstract User mapToUser(UserEntity userEntity);

  @Named("encodePassword")
  protected String encodePassword(String password) {
    return passwordEncoder.bCryptPasswordEncoder().encode(password);
  }

  @Autowired
  protected void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }
}
