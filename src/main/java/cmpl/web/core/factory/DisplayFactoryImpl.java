package cmpl.web.core.factory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.carousel.CarouselFactory;
import cmpl.web.carousel.CarouselItem;
import cmpl.web.footer.Footer;
import cmpl.web.footer.FooterFactory;
import cmpl.web.menu.MenuFactory;
import cmpl.web.menu.MenuItem;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.meta.MetaElementToDelete;
import cmpl.web.page.PAGES;
import cmpl.web.page.PageDTO;
import cmpl.web.page.PageService;

/**
 * Implementation de l'interface de factory pur generer des model and view pour les pages du site
 * 
 * @author Louis
 *
 */
public class DisplayFactoryImpl extends BaseDisplayFactoryImpl implements DisplayFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(DisplayFactoryImpl.class);
  private final MenuFactory menuFactory;
  private final FooterFactory footerFactory;
  private final MetaElementFactory metaElementFactory;
  private final CarouselFactory carouselFactory;
  private final PageService pageService;

  protected DisplayFactoryImpl(MenuFactory menuFactory, FooterFactory footerFactory,
      MetaElementFactory metaElementFactory, CarouselFactory carouselFactory, WebMessageSourceImpl messageSource,
      PageService pageService) {
    super(messageSource);
    this.menuFactory = menuFactory;
    this.footerFactory = footerFactory;
    this.metaElementFactory = metaElementFactory;
    this.carouselFactory = carouselFactory;
    this.pageService = pageService;
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param menuFactory
   * @param footerFactory
   * @param metaElementFactory
   * @param carouselFactory
   * @param messageSource
   * @return
   */
  public static DisplayFactoryImpl fromFactoriesAndMessageResource(MenuFactory menuFactory,
      FooterFactory footerFactory, MetaElementFactory metaElementFactory, CarouselFactory carouselFactory,
      WebMessageSourceImpl messageSource, PageService pageService) {
    return new DisplayFactoryImpl(menuFactory, footerFactory, metaElementFactory, carouselFactory, messageSource,
        pageService);
  }

  @Override
  public ModelAndView computeModelAndViewForPage(PAGES page, Locale locale) {

    LOGGER.info("Construction de la page  " + page.name());
    ModelAndView model = new ModelAndView(computeDecoratorFrontTileName(locale));
    model.addObject("content", computeTileName(page.getTileName(), locale));

    LOGGER.info("Construction du menu pour la page " + page.name());
    model.addObject("menuItems", computeMenuItems(page, locale));
    LOGGER.info("Construction des éléments meta pour la page  " + page.name());
    model.addObject("metaItems", computeMetaElements(locale, page));
    LOGGER.info("Construction des éléments meta open graph pour la page  " + page.name());
    model.addObject("openGraphMetaItems", computeOpenGraphMetaElements(locale, page));
    LOGGER.info("Construction du footer pour la page   " + page.name());
    model.addObject("footer", computeFooter(locale));
    LOGGER.info("Construction du titre principal pour la page  " + page.name());
    model.addObject("mainTitle", computeMainTitle(locale));
    LOGGER.info("Construction du lien du back pour la page " + page.name());
    model.addObject("hiddenLink", computeHiddenLink(locale));

    if (page.isWithCarousel()) {
      LOGGER.info("Construction du carousel pour la page " + page.name());
      model.addObject("carouselItems", computeCarouselItems(locale));
    }
    LOGGER.info("Page " + page.name() + " prête");

    return model;

  }

  public List<MenuItem> computeMenuItems(PAGES page, Locale locale) {
    return menuFactory.computeMenuItems(page, locale);
  }

  public Footer computeFooter(Locale locale) {
    return footerFactory.computeFooter(locale);
  }

  public List<MetaElementToDelete> computeMetaElements(Locale locale, PAGES page) {
    return metaElementFactory.computeMetaElementsForPage(locale, page);
  }

  protected List<MetaElementToDelete> computeMetaElementsForNewsEntry(Locale locale, PAGES page, String newsEntryId) {
    return metaElementFactory.computeMetaElementsForNewsEntry(locale, page, newsEntryId);
  }

  protected List<MetaElementToDelete> computeOpenGraphMetaElementsForNewsEntry(Locale locale, PAGES page,
      String newsEntryId) {
    return metaElementFactory.computeOpenGraphMetaElementsNewsEntry(locale, page, newsEntryId);
  }

  List<MetaElementToDelete> computeOpenGraphMetaElements(Locale locale, PAGES page) {
    return metaElementFactory.computeOpenGraphMetaElementsForPage(locale, page);
  }

  List<CarouselItem> computeCarouselItems(Locale locale) {
    List<File> imagesForCarousel = computeCarouselImagesFiles(locale);
    return carouselFactory.computeCarouselItems(imagesForCarousel);
  }

  public List<File> computeCarouselImagesFiles(Locale locale) {

    String carouselImagesSrc = getI18nValue("carousel.src", locale);
    List<String> imagesSrcs = Arrays.asList(carouselImagesSrc.split(";"));
    List<File> imagesForCarousel = new ArrayList<>();
    ClassLoader classLoader = getClass().getClassLoader();
    for (String imageSrc : imagesSrcs) {
      URL resource = classLoader.getResource(imageSrc);
      if (resource != null && StringUtils.hasText(resource.getFile())) {
        File file = new File(resource.getFile());
        imagesForCarousel.add(file);
      } else {
        LOGGER.error("Image introuvable : " + imageSrc);
      }

    }
    return imagesForCarousel;
  }

  @Override
  public ModelAndView computeModelAndViewForPage(String pageName, Locale locale) {
    LOGGER.info("Construction de la page  " + pageName);

    PageDTO page = pageService.getPageByName(pageName);

    ModelAndView model = new ModelAndView("decorator_poc");
    model.addObject("content", computePageContent(page));
    LOGGER.info("Construction du menu pour la page " + pageName);
    model.addObject("menuItems", computeMenuItems(page, locale));
    LOGGER.info("Construction des éléments meta pour la page  " + pageName);
    model.addObject("metaItems", page.getMetaElements());
    LOGGER.info("Construction des éléments meta open graph pour la page  " + pageName);
    model.addObject("openGraphMetaItems", page.getOpenGraphMetaElements());
    LOGGER.info("Construction du footer pour la page   " + pageName);
    model.addObject("footer", computeFooter(locale));
    LOGGER.info("Construction du titre principal pour la page  " + pageName);
    model.addObject("mainTitle", computeMainTitle(locale));
    LOGGER.info("Construction du lien du back pour la page " + pageName);
    model.addObject("hiddenLink", computeHiddenLink(locale));

    LOGGER.info("Page " + pageName + " prête");

    return model;
  }

  private String computePageContent(PageDTO page) {

    return page.getBody();
  }

  public List<MenuItem> computeMenuItems(PageDTO page, Locale locale) {
    return menuFactory.computeMenuItems(page, locale);
  }

}
