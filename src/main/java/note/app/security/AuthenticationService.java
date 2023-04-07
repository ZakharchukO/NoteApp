package note.app.security;

import lombok.RequiredArgsConstructor;
import note.app.domain.User;
import note.app.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final JwtService jwtService;
  private final UserService userService;
  private final AuthenticationManager authenticationManager;

  public String register(User user) {
    userService.createUser(user);
    return jwtService.generateToken(user);
  }

  public String authenticate(User user) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    User existingUser = userService.getUser(user.getEmail());
    return jwtService.generateToken(existingUser);
  }
}
