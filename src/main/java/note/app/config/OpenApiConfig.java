package note.app.config;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo.BuilderConfiguration;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.RequestHandlerProvider;
import springfox.documentation.spi.service.contexts.Orderings;
import springfox.documentation.spring.web.WebMvcRequestHandler;
import springfox.documentation.spring.web.paths.Paths;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.spring.web.readers.operation.HandlerMethodResolver;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Configuration
@EnableOpenApi
public class OpenApiConfig {

  private static final String DEFAULT_API_LOCATION = "/note-app-rest-api.yml";

  @Value("${app.swagger.api.location:}")
  private String customApiLocation;

  @Bean(name = "api")
  public Docket apiDocklet() {
    return new Docket(DocumentationType.OAS_30)
        .apiInfo(new ApiInfoBuilder().build())
        .select()
        .paths(PathSelectors.any())
        .build();
  }

  @Bean
  @Primary
  public SwaggerResourcesProvider swaggerResourcesProvider() {
    return () -> {
      SwaggerResource swaggerResource = new SwaggerResource();
      swaggerResource.setLocation(resolveYamlLocation());
      swaggerResource.setName("Note APP");
      return Collections.singletonList(swaggerResource);
    };
  }

  // Workaround for the Spring Web MVC and Springfox incompatibility, see https://github.com/springfox/springfox/issues/3462
  @Bean
  public InitializingBean removeDefaultSpringfoxHandlerProvider(
      DocumentationPluginsBootstrapper bootstrapper) {
    return () -> bootstrapper.getHandlerProviders()
        .removeIf(WebMvcRequestHandlerProvider.class::isInstance);
  }

  @Bean
  public RequestHandlerProvider customSpringfoxRequestHandlerProvider(
      Optional<ServletContext> servletContext, HandlerMethodResolver methodResolver,
      List<RequestMappingInfoHandlerMapping> handlerMappings) {
    String contextPath = servletContext.map(ServletContext::getContextPath).orElse(Paths.ROOT);

    return () -> handlerMappings.stream()
        .map(mapping -> mapping.getHandlerMethods().entrySet())
        .flatMap(Set::stream)
        .map(entry -> new WebMvcRequestHandler(contextPath, methodResolver,
            handleRequestMappingWithNullableCondition(entry.getKey()), entry.getValue()))
        .sorted(Orderings.byPatternsCondition())
        .collect(Collectors.toList());
  }

  private RequestMappingInfo handleRequestMappingWithNullableCondition(
      RequestMappingInfo requestMappingInfo) {
    PathPatternsRequestCondition pathPatternsCondition = requestMappingInfo.getPathPatternsCondition();
    if (pathPatternsCondition == null) {
      return requestMappingInfo;
    }

    String[] patterns = pathPatternsCondition.getPatternValues().toArray(String[]::new);
    return requestMappingInfo.mutate().options(new BuilderConfiguration()).paths(patterns).build();
  }

  private String resolveYamlLocation() {
    if (customApiLocation.isBlank()) {
      return DEFAULT_API_LOCATION;
    }

    return customApiLocation;
  }
}
