package cmpl.web.factory.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  public static DisplayFactoryImpl fromFactoriesAndMessageResource(MenuFactory menuFactory, FooterFactory footerFactory,
      MetaElementFactory metaElementFactory, CarouselFactory carouselFactory, WebMessageSourceImpl messageSource) {
    return new DisplayFactoryImpl(menuFactory, footerFactory, metaElementFactory, carouselFactory, messageSource);
  }

  @Override
  public ModelAndView computeModelAndViewForPage(PAGE page, Locale locale) {

    LOGGER.info("Construction de la page  " + page.name());
    ModelAndView model = new ModelAndView(computeTileName(page.getTileName(), locale));

    LOGGER.info("Construction du menu pour la page " + page.name());
    model.addObject("menuItems", computeMenuItems(page, locale));
    LOGGER.info("Construction des éléments meta pour la page  " + page.name());
    model.addObject("metaItems", computeMetaElements(locale, page));
    LOGGER.info("Construction du footer pour la page   " + page.name());
    model.addObject("footer", computeFooter(locale));
    LOGGER.info("Construction du titre principal pour la page  " + page.name());
    model.addObject("maintTitle", computeMainTitle(locale));
    LOGGER.info("Construction du lien du back pour la page " + page.name());
    model.addObject("hiddenLink", computeHiddenLink(locale));

    if (page.isWithCarousel()) {
      LOGGER.info("Construction du carousel pour la page " + page.name());
      model.addObject("carouselItems", computeCarouselItems());
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

  List<CarouselItem> computeCarouselItems() {
    List<File> imagesForCarousel = new ArrayList<>();
    imagesForCarousel.add(new File("src/main/resources/static/img/logo/logo.jpg"));
    imagesForCarousel.add(new File("src/main/resources/static/img/logo/logoSmall.jpg"));
    return carouselFactory.computeCarouselItems(imagesForCarousel);
  }

}
