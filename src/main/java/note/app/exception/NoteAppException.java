package note.app.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoteAppException extends RuntimeException {

  private final ErrorCode errorCode;

  @Builder
  private NoteAppException(ErrorCode errorCode) {
    super(errorCode.toString());
    this.errorCode = errorCode;
  }

  public static NoteAppException with(ErrorCode errorCode) {
    return NoteAppException.builder()
        .errorCode(errorCode)
        .build();
  }
}
