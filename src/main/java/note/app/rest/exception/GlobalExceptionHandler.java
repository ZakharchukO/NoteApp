package note.app.rest.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import note.app.exception.ErrorCode;
import note.app.exception.NoteAppException;
import note.app.rest.domain.generated.ErrorTO;
import note.app.rest.exception.message.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final MessageService messageService;

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorTO> handleException(Exception ex, HandlerMethod handlerMethod) {
    log.warn("Uncaught exception handled in Controller: '{}', message: '{}', stackTrace: '{}'",
        handlerMethod.getMethod().getDeclaringClass().getSimpleName(), ex.getMessage(), ex);
    ErrorCode errorCode = ErrorCode.TECHNICAL_ERROR;
    return buildErrorResponseEntity(errorCode);
  }

  @ExceptionHandler(NoteAppException.class)
  public ResponseEntity<ErrorTO> handleNoteAppException(NoteAppException e) {
    log.info("Handled Note APP exception with error code '{}'", e.getErrorCode());
    ErrorCode errorCode = e.getErrorCode();
    return buildErrorResponseEntity(errorCode);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorTO> handleExceptiofn(Exception ex, HandlerMethod handlerMethod) {
    log.warn("Exception handled in Controller: '{}', message: '{}', stackTrace: '{}'",
        handlerMethod.getMethod().getDeclaringClass().getSimpleName(), ex.getMessage(), ex);
    ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
    return buildErrorResponseEntity(errorCode);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorTO> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, HandlerMethod handlerMethod) {
    log.warn(
        "Invalid method argument exception handled in controller: '{}', message: '{}', stackTrace: '{}'",
        handlerMethod.getMethod().getDeclaringClass().getSimpleName(), ex.getMessage(), ex);
    ErrorCode errorCode = ErrorCode.WRONG_REQUEST;
    return buildErrorResponseEntity(errorCode);
  }

  private ResponseEntity<ErrorTO> buildErrorResponseEntity(ErrorCode errorCode) {
    String message = messageService.getMessage(errorCode.name());
    return ResponseEntity.status(errorCode.getHttpStatus())
        .body(buildErrorBody(errorCode, message));
  }

  private ErrorTO buildErrorBody(ErrorCode code, String message) {
    return new ErrorTO()
        .errorCode(code.name())
        .message(message);
  }
}
