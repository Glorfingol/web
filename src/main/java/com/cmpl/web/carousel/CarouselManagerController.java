package com.cmpl.web.carousel;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.model.BaseException;
import com.cmpl.web.page.BACK_PAGE;

@Controller
@RequestMapping(value = "/manager/carousels")
public class CarouselManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CarouselManagerController.class);

  private final CarouselDispatcher carouselDispatcher;
  private final CarouselManagerDisplayFactory carouselDisplayFactory;

  @Autowired
  public CarouselManagerController(CarouselDispatcher carouselDispatcher,
      CarouselManagerDisplayFactory carouselDisplayFactory) {
    this.carouselDisplayFactory = carouselDisplayFactory;
    this.carouselDispatcher = carouselDispatcher;
  }

  @GetMapping
  public ModelAndView printViewCarousels(@RequestParam(name = "p", required = false) Integer pageNumber) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.CAROUSELS_VIEW.name());
    return carouselDisplayFactory.computeModelAndViewForViewAllCarousels(Locale.FRANCE, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  public ModelAndView printCreateCarousel() {
    LOGGER.info("Accès à la page de création des carousels");
    return carouselDisplayFactory.computeModelAndViewForCreateCarousel(Locale.FRANCE);
  }

  @PostMapping
  @ResponseBody
  public ResponseEntity<CarouselResponse> createCarousel(@RequestBody CarouselCreateForm createForm) {

    LOGGER.info("Tentative de création d'un carousel");
    try {
      CarouselResponse response = carouselDispatcher.createEntity(createForm, Locale.FRANCE);
      if (response.getCarousel() != null) {
        LOGGER.info("Entrée crée, id " + response.getCarousel().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @PutMapping(value = "/{carouselId}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<CarouselResponse> updateCarousel(@RequestBody CarouselUpdateForm updateForm) {

    LOGGER.info("Tentative de modification d'un carousel");
    try {
      CarouselResponse response = carouselDispatcher.updateEntity(updateForm, Locale.FRANCE);
      if (response.getCarousel() != null) {
        LOGGER.info("Entrée modifiée, id " + response.getCarousel().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @GetMapping(value = "/{carouselId}")
  public ModelAndView printViewUpdateCarousel(@PathVariable(value = "carouselId") String carouselId) {
    LOGGER.info("Accès à la page " + BACK_PAGE.CAROUSELS_VIEW.name() + " pour " + carouselId);
    return carouselDisplayFactory.computeModelAndViewForUpdateCarousel(Locale.FRANCE, carouselId);
  }

  @GetMapping(value = "/{carouselId}/_main")
  public ModelAndView printViewUpdateCarouselMain(@PathVariable(value = "carouselId") String carouselId) {
    LOGGER
        .info("Accès à la page " + BACK_PAGE.CAROUSELS_UPDATE.name() + " pour " + carouselId + " pour la partie main");
    return carouselDisplayFactory.computeModelAndViewForUpdateCarouselMain(Locale.FRANCE, carouselId);
  }

  @GetMapping(value = "/{carouselId}/_items")
  public ModelAndView printViewUpdateCarouselItems(@PathVariable(value = "carouselId") String carouselId) {
    LOGGER.info("Accès à la page " + BACK_PAGE.CAROUSELS_UPDATE.name() + " pour " + carouselId
        + " pour la partie items");
    return carouselDisplayFactory.computeModelAndViewForUpdateCarouselItems(Locale.FRANCE, carouselId);
  }

  @PostMapping(value = "/{carouselId}/items")
  @ResponseBody
  public ResponseEntity<CarouselItemResponse> createCarouselItem(@RequestBody CarouselItemCreateForm createForm) {

    LOGGER.info("Tentative de création d'un élément de carousel");
    try {
      CarouselItemResponse response = carouselDispatcher.createEntity(createForm, Locale.FRANCE);
      if (response.getItem() != null) {
        LOGGER.info("Entrée crée, id " + response.getItem().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/{carouselId}/items/{carouselItemId}")
  @ResponseBody
  public ResponseEntity<CarouselItemResponse> deleteCarouselItem(
      @PathVariable(value = "carouselItemId") String carouselItemId, @RequestBody CarouselItemCreateForm createForm) {

    LOGGER.info("Tentative de suppression d'un élément de carousel");
    try {
      carouselDispatcher.deleteCarouselItemEntity(carouselItemId, Locale.FRANCE);
    } catch (BaseException e) {
      LOGGER.error("Echec de la suppression de l'élément de carousel " + carouselItemId, e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
