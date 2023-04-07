package note.app.rest.mapper;

import note.app.domain.User;
import note.app.rest.domain.generated.CustomerTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerTOMapper {

  @Mapping(target = "role", constant = "USER")
  User mapToUser(CustomerTO customerTO);
}
