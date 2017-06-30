package cmpl.web.factory.impl;

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

import cmpl.web.factory.CarouselFactory;
import cmpl.web.factory.DisplayFactory;
import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.carousel.CarouselItem;
import cmpl.web.model.footer.Footer;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.page.PAGE;

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

  protected DisplayFactoryImpl(MenuFactory menuFactory, FooterFactory footerFactory,
      MetaElementFactory metaElementFactory, CarouselFactory carouselFactory, WebMessageSourceImpl messageSource) {
    super(messageSource);
    this.menuFactory = menuFactory;
    this.footerFactory = footerFactory;
    this.metaElementFactory = metaElementFactory;
    this.carouselFactory = carouselFactory;
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
      WebMessageSourceImpl messageSource) {
    return new DisplayFactoryImpl(menuFactory, footerFactory, metaElementFactory, carouselFactory, messageSource);
  }

  @Override
  public ModelAndView computeModelAndViewForPage(PAGE page, Locale locale) {

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

  List<MenuItem> computeMenuItems(PAGE page, Locale locale) {
    return menuFactory.computeMenuItems(page, locale);
  }

  Footer computeFooter(Locale locale) {
    return footerFactory.computeFooter(locale);
  }

  List<MetaElement> computeMetaElements(Locale locale, PAGE page) {
    return metaElementFactory.computeMetaElementsForPage(locale, page);
  }

  List<MetaElement> computeMetaElementsForNewsEntry(Locale locale, PAGE page, String newsEntryId) {
    return metaElementFactory.computeMetaElementsForNewsEntry(locale, page, newsEntryId);
  }

  List<MetaElement> computeOpenGraphMetaElementsForNewsEntry(Locale locale, PAGE page, String newsEntryId) {
    return metaElementFactory.computeOpenGraphMetaElementsNewsEntry(locale, page, newsEntryId);
  }

  List<MetaElement> computeOpenGraphMetaElements(Locale locale, PAGE page) {
    return metaElementFactory.computeOpenGraphMetaElementsForPage(locale, page);
  }

  List<CarouselItem> computeCarouselItems(Locale locale) {
    List<File> imagesForCarousel = computeCarouselImagesFiles(locale);
    return carouselFactory.computeCarouselItems(imagesForCarousel);
  }

  List<File> computeCarouselImagesFiles(Locale locale) {

    String carouselImagesSrc = getI18nValue("carousel.src", locale);
    List<String> imagesSrcs = Arrays.asList(carouselImagesSrc.split(";"));
    List<File> imagesForCarousel = new ArrayList<>();
    ClassLoader classLoader = getClass().getClassLoader();
    for (String imageSrc : imagesSrcs) {
      URL resource = classLoader.getResource(imageSrc);
      if (resource != null && !StringUtils.isEmpty(resource.getFile())) {
        File file = new File(resource.getFile());
        imagesForCarousel.add(file);
      } else {
        LOGGER.error("Image introuvable : " + imageSrc);
      }

    }
    return imagesForCarousel;
  }

}
