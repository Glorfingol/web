package cmpl.web.carousel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarouselConfiguration {

  @Bean
  CarouselFactory carouselFactory(CarouselService carouselService) {
    return new CarouselFactoryImpl(carouselService);
  }

  @Bean
  CarouselItemService carouselItemService(CarouselItemRepository carouselItemRepository) {
    return new CarouselItemServiceImpl(carouselItemRepository);
  }

  @Bean
  CarouselService carouselService(CarouselRepository carouselRepository, CarouselItemService carouselItemService) {
    return new CarouselServiceImpl(carouselRepository, carouselItemService);
  }

}
