package com.cmpl.web.carousel;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

public interface CarouselManagerDisplayFactory {

  ModelAndView computeModelAndViewForViewAllCarousels(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForUpdateCarousel(Locale locale, String carouselId);

  ModelAndView computeModelAndViewForUpdateCarouselMain(String carouselId);

  ModelAndView computeModelAndViewForUpdateCarouselItems(String carouselId);

  ModelAndView computeModelAndViewForCreateCarousel(Locale locale);

}
