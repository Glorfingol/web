package cmpl.web.carousel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.media.MediaService;

@Configuration
public class CarouselConfiguration {

  @Bean
  CarouselItemService carouselItemService(CarouselItemRepository carouselItemRepository, MediaService mediaService) {
    return new CarouselItemServiceImpl(carouselItemRepository, mediaService);
  }

  @Bean
  CarouselService carouselService(CarouselRepository carouselRepository, CarouselItemService carouselItemService) {
    return new CarouselServiceImpl(carouselRepository, carouselItemService);
  }

}
