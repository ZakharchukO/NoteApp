package note.app.rest.exception.message;

import static java.util.Locale.forLanguageTag;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

@Slf4j
@AllArgsConstructor
public class MessageService {

  private static final String DEFAULT_LANGUAGE_TAG = "en";

  private final MessageSource messageSource;

  public String getMessage(String code) {
    return getMessage(code, null);
  }

  public String getMessage(String code, List<String> messageParams) {
    try {
      return messageSource
          .getMessage(code, convertToArray(messageParams), forLanguageTag(DEFAULT_LANGUAGE_TAG));
    } catch (NoSuchMessageException e) {
      log.info("Can't get message: {}", e.getMessage());
    }
    return "";
  }

  private String[] convertToArray(List<String> parameters) {
    return CollectionUtils.isNotEmpty(parameters)
        ? parameters.toArray(String[]::new)
        : null;
  }
}
