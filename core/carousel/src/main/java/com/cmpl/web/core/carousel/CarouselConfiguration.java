package com.cmpl.web.core.carousel;

import com.cmpl.web.core.carousel.item.CarouselItemDAO;
import com.cmpl.web.core.carousel.item.CarouselItemMapper;
import com.cmpl.web.core.carousel.item.CarouselItemRepository;
import com.cmpl.web.core.carousel.item.CarouselItemService;
import com.cmpl.web.core.carousel.item.DefaultCarouselItemDAO;
import com.cmpl.web.core.carousel.item.DefaultCarouselItemService;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.models.Carousel;
import com.cmpl.web.core.models.CarouselItem;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {Carousel.class, CarouselItem.class})
@EnableJpaRepositories(basePackageClasses = {CarouselRepository.class,
  CarouselItemRepository.class})
public class CarouselConfiguration {

  @Bean
  public CarouselItemDAO carouselItemDAO(CarouselItemRepository carouselItemRepository,
    ApplicationEventPublisher publisher) {
    return new DefaultCarouselItemDAO(carouselItemRepository, publisher);
  }

  @Bean
  public CarouselItemMapper carouselItemMapper(MediaService mediaService) {
    return new CarouselItemMapper(mediaService);
  }

  @Bean
  public CarouselItemService carouselItemService(CarouselItemDAO carouselItemDAO,
    CarouselItemMapper carouselItemMapper) {
    return new DefaultCarouselItemService(carouselItemDAO, carouselItemMapper);
  }

  @Bean
  public BackMenuItem carouselsBackMenuItem(BackMenuItem webmastering,
    Privilege carouselsReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/carousels").label("back.carousels.label")
      .title("back.carousels.title").iconClass("fa fa-files-o").parent(webmastering).order(2)
      .privilege(carouselsReadPrivilege.privilege()).build();
  }

  @Bean
  public CarouselDAO carouselDAO(CarouselRepository carouselRepository,
    ApplicationEventPublisher publisher) {
    return new DefaultCarouselDAO(carouselRepository, publisher);
  }

  @Bean
  public CarouselMapper carouselMapper(CarouselItemService carouselItemService) {
    return new CarouselMapper(carouselItemService);
  }

  @Bean
  public CarouselService carouselService(CarouselDAO carouselDAO, CarouselMapper carouselMapper) {
    return new DefaultCarouselService(carouselDAO, carouselMapper);
  }

  @Bean
  public CarouselTranslator carouselTranslator() {
    return new DefaultCarouselTranslator();
  }

  @Bean
  public CarouselDispatcher carouselDispatcher(CarouselService carouselService,
    CarouselItemService carouselItemService,
    CarouselTranslator carouselTranslator, MediaService mediaService) {
    return new DefaultCarouselDispatcher(carouselService, carouselItemService, mediaService,
      carouselTranslator);
  }

}
