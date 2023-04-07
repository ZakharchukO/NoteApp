package note.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
  TECHNICAL_ERROR,
  INVALID_TOKEN(HttpStatus.FORBIDDEN),
  TOKEN_EXPIRED(HttpStatus.FORBIDDEN),
  WRONG_REQUEST(HttpStatus.BAD_REQUEST),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
  NOTE_NOT_FOUND(HttpStatus.NOT_FOUND),
  USER_ALREADY_EXIST(HttpStatus.CONFLICT),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND);

  @Getter
  private final HttpStatus httpStatus;

  ErrorCode(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

  ErrorCode() {
    this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
