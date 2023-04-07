package note.app.rest.exception.message;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

  private static final int MESSAGE_CACHE_SECONDS = 60 * 60; // reload messages every hour
  private static final String MESSAGE_RESOURCE_BASE_NAME = "classpath:error_message";
  private static final Charset MESSAGE_ENCODING = StandardCharsets.UTF_8;

  @Bean
  public MessageService messageService() {
    return new MessageService(messageSource());
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename(MESSAGE_RESOURCE_BASE_NAME);
    messageSource.setCacheSeconds(MESSAGE_CACHE_SECONDS);
    messageSource.setDefaultEncoding(MESSAGE_ENCODING.displayName());
    return messageSource;
  }
}
