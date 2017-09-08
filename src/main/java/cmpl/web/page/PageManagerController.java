package cmpl.web.page;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller pour la gestion des pages dans le back office
 * 
 * @author Louis
 *
 */
@Controller
public class PageManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PageManagerController.class);

  private final PagesManagerDisplayFactory pagesManagerDisplayFactory;
  private final PageDispatcher pageDispatcher;

  @Autowired
  public PageManagerController(PagesManagerDisplayFactory pagesManagerDisplayFactory, PageDispatcher pageDispatcher) {
    this.pagesManagerDisplayFactory = pagesManagerDisplayFactory;
    this.pageDispatcher = pageDispatcher;
  }

  /**
   * Mapping pour la page d'affichage de toute les NewsEntry
   * 
   * @return
   */
  @GetMapping(value = "/manager/pages")
  public ModelAndView printViewPages(@RequestParam(name = "p", required = false) Integer pageNumber) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_VIEW.name());
    return pagesManagerDisplayFactory.computeModelAndViewForViewAllPages(BACK_PAGE.PAGES_VIEW, Locale.FRANCE,
        pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/manager/pages/_create")
  public ModelAndView printCreatePage() {
    LOGGER.info("Accès à la page de création des pages");
    return pagesManagerDisplayFactory.computeModelAndViewForCreatePage(BACK_PAGE.PAGES_CREATE, Locale.FRANCE);
  }

  @PostMapping(value = "/manager/pages", produces = "application/json")
  @ResponseBody
  public ResponseEntity<PageResponse> createPage(@RequestBody PageCreateForm createForm) {

    LOGGER.info("Tentative de création d'une page");
    try {
      PageResponse response = pageDispatcher.createEntity(createForm, Locale.FRANCE);
      if (response.getPage() != null) {
        LOGGER.info("Entrée crée, id " + response.getPage().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @GetMapping(value = "/manager/pages/{pageId}")
  public ModelAndView printViewUpdatePage(@PathVariable(value = "pageId") String pageId) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId);
    return pagesManagerDisplayFactory.computeModelAndViewForUpdatePage(BACK_PAGE.PAGES_UPDATE, Locale.FRANCE, pageId);
  }

  @GetMapping(value = "/manager/pages/{pageId}/_main")
  public ModelAndView printViewUpdatePageMain(@PathVariable(value = "pageId") String pageId) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie main");
    return pagesManagerDisplayFactory.computeModelAndViewForUpdatePageMain(Locale.FRANCE, pageId);
  }

  @GetMapping(value = "/manager/pages/{pageId}/_body")
  public ModelAndView printViewUpdatePageBody(@PathVariable(value = "pageId") String pageId) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie body");
    return pagesManagerDisplayFactory.computeModelAndViewForUpdatePageBody(Locale.FRANCE, pageId);
  }

  @GetMapping(value = "/manager/pages/{pageId}/_meta")
  public ModelAndView printViewUpdatePageMeta(@PathVariable(value = "pageId") String pageId) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie meta");
    return pagesManagerDisplayFactory.computeModelAndViewForUpdatePageMeta(Locale.FRANCE, pageId);
  }

}
