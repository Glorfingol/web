package cmpl.web.carousel;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.page.BACK_PAGE;

public interface CarouselManagerDisplayFactory {

  ModelAndView computeModelAndViewForViewAllCarousels(BACK_PAGE backPage, Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForUpdateCarousel(BACK_PAGE backPage, Locale locale, String carouselId);

  ModelAndView computeModelAndViewForUpdateCarouselMain(Locale locale, String carouselId);

  ModelAndView computeModelAndViewForUpdateCarouselItems(Locale locale, String carouselId);

  ModelAndView computeModelAndViewForCreateCarousel(BACK_PAGE backPage, Locale locale);

}
