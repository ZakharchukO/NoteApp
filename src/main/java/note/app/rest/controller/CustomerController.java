package note.app.rest.controller;

import lombok.RequiredArgsConstructor;
import note.app.domain.User;
import note.app.rest.api.generated.CustomerApi;
import note.app.rest.domain.generated.CustomerTO;
import note.app.rest.mapper.CustomerTOMapper;
import note.app.security.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

  private final CustomerTOMapper customerTOMapper;
  private final AuthenticationService authenticationService;

  @Override
  public ResponseEntity<String> register(CustomerTO body) {
    User user = customerTOMapper.mapToUser(body);
    return ResponseEntity.ok(authenticationService.register(user));
  }

  @Override
  public ResponseEntity<String> authenticate(CustomerTO body) {
    User user = customerTOMapper.mapToUser(body);
    return ResponseEntity.ok(authenticationService.authenticate(user));
  }
}
