package cmpl.web.meta;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.news.NewsEntryService;

@Configuration
public class MetaConfiguration {

  @Bean
  MetaElementService metaElementService(MetaElementRepository repository) {
    return new MetaElementServiceImpl(repository);
  }

  @Bean
  MetaElementFactory metaElementFactory(WebMessageSourceImpl messageSource, NewsEntryService newsEntryService) {
    return new MetaElementFactoryImpl(messageSource, newsEntryService);
  }

  @Bean
  OpenGraphMetaElementService openGraphMetaElementService(OpenGraphMetaElementRepository repository) {
    return new OpenGraphMetaElementServiceImpl(repository);
  }

}
