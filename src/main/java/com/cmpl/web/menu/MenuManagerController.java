package com.cmpl.web.menu;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.page.BACK_PAGE;

@Controller
@RequestMapping(value = "/manager/menus")
public class MenuManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MenuManagerController.class);

  private final MenuDispatcher dispatcher;
  private final MenuManagerDisplayFactory displayFactory;

  @Autowired
  public MenuManagerController(MenuDispatcher dispatcher, MenuManagerDisplayFactory displayFactory) {
    this.dispatcher = dispatcher;
    this.displayFactory = displayFactory;
  }

  @GetMapping
  public ModelAndView printViewMenus(@RequestParam(name = "p", required = false) Integer pageNumber) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.MENUS_VIEW.name());
    return displayFactory.computeModelAndViewForViewAllMenus(Locale.FRANCE, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  public ModelAndView printCreateMenu() {
    LOGGER.info("Accès à la page de création des menus");
    return displayFactory.computeModelAndViewForCreateMenu(Locale.FRANCE);
  }

  @GetMapping(value = "/{menuId}")
  public ModelAndView printViewUpdateMenu(@PathVariable(value = "menuId") String menuId) {
    LOGGER.info("Accès à la page " + BACK_PAGE.MENUS_UPDATE.name() + " pour " + menuId);
    return displayFactory.computeModelAndViewForUpdateMenu(Locale.FRANCE, menuId);
  }

  @PutMapping(value = "/{menuId}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<MenuResponse> updateMenu(@RequestBody MenuUpdateForm updateForm) {

    LOGGER.info("Tentative de modification d'un menu");
    try {
      MenuResponse response = dispatcher.updateEntity(updateForm, Locale.FRANCE);
      if (response.getMenu() != null) {
        LOGGER.info("Entrée modifiée, id " + response.getMenu().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @PostMapping
  @ResponseBody
  public ResponseEntity<MenuResponse> createMenu(@RequestBody MenuCreateForm createForm) {

    LOGGER.info("Tentative de modification d'un menu");
    try {
      MenuResponse response = dispatcher.createEntity(createForm, Locale.FRANCE);
      if (response.getMenu() != null) {
        LOGGER.info("Entrée créee, id " + response.getMenu().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la création de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }
}
