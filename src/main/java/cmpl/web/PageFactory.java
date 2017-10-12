package cmpl.web;

import java.util.Locale;

import cmpl.web.carousel.Carousel;
import cmpl.web.carousel.CarouselItem;
import cmpl.web.carousel.CarouselItemRepository;
import cmpl.web.carousel.CarouselRepository;
import cmpl.web.media.Media;
import cmpl.web.media.MediaRepository;
import cmpl.web.menu.Menu;
import cmpl.web.menu.MenuRepository;
import cmpl.web.meta.MetaElement;
import cmpl.web.meta.MetaElementRepository;
import cmpl.web.meta.OpenGraphMetaElement;
import cmpl.web.meta.OpenGraphMetaElementRepository;
import cmpl.web.page.Page;
import cmpl.web.page.PageRepository;

public class PageFactory {

  private static final String OG_TITLE = "og:title";
  private static final String OG_DESCRIPTION = "og:description";
  private static final String DESCRIPTION = "description";
  private static final String TITLE = "title";
  private static final String VIEWPORT = "viewport";
  private static final String LANGUAGE = "language";
  private static final String WIDTH_DEVICE_WIDTH_INITIAL_SCALE_1_MAXIMUM_SCALE_1_USER_SCALABLE_NO = "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no";

  public static void createPages(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository,
      CarouselRepository carouselRepository, CarouselItemRepository carouselItemRepository,
      MediaRepository mediaRepository) {
    createIndex(pageRepository, menuRepository, metaElementRepository, openGraphMetaElementRepository,
        carouselRepository, carouselItemRepository, mediaRepository);
    createActualites(pageRepository, menuRepository, metaElementRepository, openGraphMetaElementRepository);
    createAppointment(pageRepository, menuRepository, metaElementRepository, openGraphMetaElementRepository);
    createCenter(pageRepository, menuRepository, metaElementRepository, openGraphMetaElementRepository);
    createContact(pageRepository, menuRepository, metaElementRepository, openGraphMetaElementRepository);
    createGynecologist(pageRepository, menuRepository, metaElementRepository, openGraphMetaElementRepository);
    createMedicalCare(pageRepository, menuRepository, metaElementRepository, openGraphMetaElementRepository);
    createOpeningHours(pageRepository, menuRepository, metaElementRepository, openGraphMetaElementRepository);
    createPrices(pageRepository, menuRepository, metaElementRepository, openGraphMetaElementRepository);
    createTechnics(pageRepository, menuRepository, metaElementRepository, openGraphMetaElementRepository);

  }

  public static void createIndex(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository,
      CarouselRepository carouselRepository, CarouselItemRepository carouselItemRepository,
      MediaRepository mediaRepository) {

    Page index = new Page();
    index.setMenuTitle("Accueil");
    index.setName("accueil");
    index = pageRepository.save(index);
    String pageId = String.valueOf(index.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + index.getName());
    menu.setLabel(index.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(1);
    menu.setTitle(index.getMenuTitle());

    menuRepository.save(menu);

    MetaElement language = computeLanguage(Locale.FRANCE, pageId);
    metaElementRepository.save(language);

    MetaElement viewport = computeViewPort(pageId);
    metaElementRepository.save(viewport);

    metaElementRepository.save(computeMetaElement(TITLE, index.getMenuTitle(), pageId));
    metaElementRepository.save(computeMetaElement(DESCRIPTION, index.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_TITLE, index.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_DESCRIPTION, index.getMenuTitle(), pageId));

    Carousel carouselHome = new Carousel();
    carouselHome.setName("home");
    carouselHome.setPageId(pageId);

    carouselHome = carouselRepository.save(carouselHome);
    String carouselId = String.valueOf(carouselHome.getId());

    Media firstMedia = new Media();
    firstMedia.setContentType("image/jpg");
    firstMedia.setExtension(".jpg");
    firstMedia.setName("epilation_verso.jpg");
    firstMedia.setSrc("http://poc.lperrod.cardiweb.com/static/medias/epilation_verso.jpg");
    firstMedia.setSize(114688l);

    firstMedia = mediaRepository.save(firstMedia);

    CarouselItem firstImage = new CarouselItem();
    firstImage.setMediaId(String.valueOf(firstMedia.getId()));
    firstImage.setCarouselId(carouselId);
    firstImage.setOrderInCarousel(1);

    Media secondMedia = new Media();
    secondMedia.setContentType("image/jpg");
    secondMedia.setExtension(".jpg");
    secondMedia.setName("epilation_verso.jpg");
    secondMedia.setSrc("http://poc.lperrod.cardiweb.com/static/medias/epilation_recto.jpg");
    secondMedia.setSize(61440l);

    secondMedia = mediaRepository.save(secondMedia);

    carouselItemRepository.save(firstImage);

    CarouselItem secondImage = new CarouselItem();
    secondImage.setMediaId(String.valueOf(secondMedia.getId()));
    secondImage.setOrderInCarousel(2);
    secondImage.setCarouselId(carouselId);

    carouselItemRepository.save(secondImage);

  }

  public static void createActualites(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

    Page actualites = new Page();
    actualites.setMenuTitle("Actualites");
    actualites.setName("actualites");
    actualites.setWithNews(true);
    actualites = pageRepository.save(actualites);

    String pageId = String.valueOf(actualites.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + actualites.getName());
    menu.setLabel(actualites.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(7);
    menu.setTitle(actualites.getMenuTitle());

    menuRepository.save(menu);

    MetaElement language = computeLanguage(Locale.FRANCE, pageId);
    metaElementRepository.save(language);

    MetaElement viewport = computeViewPort(pageId);
    metaElementRepository.save(viewport);

    metaElementRepository.save(computeMetaElement(TITLE, actualites.getMenuTitle(), pageId));
    metaElementRepository.save(computeMetaElement(DESCRIPTION, actualites.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_TITLE, actualites.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_DESCRIPTION, actualites.getMenuTitle(), pageId));

  }

  public static void createAppointment(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

    Page appointment = new Page();
    appointment.setMenuTitle("Prendre rendez-vous");
    appointment.setName("rendez_vous");
    appointment = pageRepository.save(appointment);
    String pageId = String.valueOf(appointment.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + appointment.getName());
    menu.setLabel(appointment.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(9);
    menu.setTitle(appointment.getMenuTitle());

    menuRepository.save(menu);

    MetaElement language = computeLanguage(Locale.FRANCE, pageId);
    metaElementRepository.save(language);

    MetaElement viewport = computeViewPort(pageId);
    metaElementRepository.save(viewport);

    metaElementRepository.save(computeMetaElement(TITLE, appointment.getMenuTitle(), pageId));
    metaElementRepository.save(computeMetaElement(DESCRIPTION, appointment.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_TITLE, appointment.getMenuTitle(), pageId));
    openGraphMetaElementRepository
        .save(computeOpenGraphMetaElement(OG_DESCRIPTION, appointment.getMenuTitle(), pageId));

  }

  public static void createOpeningHours(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

    Page openingHours = new Page();
    openingHours.setMenuTitle("Horaires");
    openingHours.setName("horaires");
    openingHours = pageRepository.save(openingHours);
    String pageId = String.valueOf(openingHours.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + openingHours.getName());
    menu.setLabel(openingHours.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(10);
    menu.setTitle(openingHours.getMenuTitle());

    menuRepository.save(menu);

    MetaElement language = computeLanguage(Locale.FRANCE, pageId);
    metaElementRepository.save(language);

    MetaElement viewport = computeViewPort(pageId);
    metaElementRepository.save(viewport);

    metaElementRepository.save(computeMetaElement(TITLE, openingHours.getMenuTitle(), pageId));
    metaElementRepository.save(computeMetaElement(DESCRIPTION, openingHours.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_TITLE, openingHours.getMenuTitle(), pageId));
    openGraphMetaElementRepository
        .save(computeOpenGraphMetaElement(OG_DESCRIPTION, openingHours.getMenuTitle(), pageId));

  }

  public static void createCenter(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {
    Page center = new Page();
    center.setMenuTitle("Le centre");
    center.setName("centre_medical");
    center = pageRepository.save(center);
    String pageId = String.valueOf(center.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + center.getName());
    menu.setLabel(center.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(2);
    menu.setTitle(center.getMenuTitle());

    menuRepository.save(menu);

    MetaElement language = computeLanguage(Locale.FRANCE, pageId);
    metaElementRepository.save(language);

    MetaElement viewport = computeViewPort(pageId);
    metaElementRepository.save(viewport);

    metaElementRepository.save(computeMetaElement(TITLE, center.getMenuTitle(), pageId));
    metaElementRepository.save(computeMetaElement(DESCRIPTION, center.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_TITLE, center.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_DESCRIPTION, center.getMenuTitle(), pageId));

  }

  public static void createPrices(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

    Page prices = new Page();
    prices.setMenuTitle("Tarifs des soins");
    prices.setName("tarifs");
    prices = pageRepository.save(prices);
    String pageId = String.valueOf(prices.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + prices.getName());
    menu.setLabel(prices.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(6);
    menu.setTitle(prices.getMenuTitle());

    menuRepository.save(menu);

    MetaElement language = computeLanguage(Locale.FRANCE, pageId);
    metaElementRepository.save(language);

    MetaElement viewport = computeViewPort(pageId);
    metaElementRepository.save(viewport);

    metaElementRepository.save(computeMetaElement(TITLE, prices.getMenuTitle(), pageId));
    metaElementRepository.save(computeMetaElement(DESCRIPTION, prices.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_TITLE, prices.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_DESCRIPTION, prices.getMenuTitle(), pageId));

  }

  public static void createContact(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

    Page contact = new Page();
    contact.setMenuTitle("Contact");
    contact.setName("contact");
    contact = pageRepository.save(contact);
    String pageId = String.valueOf(contact.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + contact.getName());
    menu.setLabel(contact.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(8);
    menu.setTitle(contact.getMenuTitle());

    menuRepository.save(menu);

    MetaElement language = computeLanguage(Locale.FRANCE, pageId);
    metaElementRepository.save(language);

    MetaElement viewport = computeViewPort(pageId);
    metaElementRepository.save(viewport);

    metaElementRepository.save(computeMetaElement(TITLE, contact.getMenuTitle(), pageId));
    metaElementRepository.save(computeMetaElement(DESCRIPTION, contact.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_TITLE, contact.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_DESCRIPTION, contact.getMenuTitle(), pageId));

  }

  public static void createMedicalCare(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

    Page medicalCare = new Page();
    medicalCare.setMenuTitle("Soins medicaux");
    medicalCare.setName("soins_medicaux");
    medicalCare = pageRepository.save(medicalCare);
    String pageId = String.valueOf(medicalCare.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + medicalCare.getName());
    menu.setLabel(medicalCare.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(4);
    menu.setTitle(medicalCare.getMenuTitle());

    menuRepository.save(menu);

    MetaElement language = computeLanguage(Locale.FRANCE, pageId);
    metaElementRepository.save(language);

    MetaElement viewport = computeViewPort(pageId);
    metaElementRepository.save(viewport);

    metaElementRepository.save(computeMetaElement(TITLE, medicalCare.getMenuTitle(), pageId));
    metaElementRepository.save(computeMetaElement(DESCRIPTION, medicalCare.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_TITLE, medicalCare.getMenuTitle(), pageId));
    openGraphMetaElementRepository
        .save(computeOpenGraphMetaElement(OG_DESCRIPTION, medicalCare.getMenuTitle(), pageId));

  }

  public static void createGynecologist(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

    Page gynecologist = new Page();
    gynecologist.setMenuTitle("Gynecologue");
    gynecologist.setName("gynecologue");
    gynecologist = pageRepository.save(gynecologist);
    String pageId = String.valueOf(gynecologist.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + gynecologist.getName());
    menu.setLabel(gynecologist.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(3);
    menu.setTitle(gynecologist.getMenuTitle());

    menuRepository.save(menu);

    MetaElement language = computeLanguage(Locale.FRANCE, pageId);
    metaElementRepository.save(language);

    MetaElement viewport = computeViewPort(pageId);
    metaElementRepository.save(viewport);

    metaElementRepository.save(computeMetaElement(TITLE, gynecologist.getMenuTitle(), pageId));
    metaElementRepository.save(computeMetaElement(DESCRIPTION, gynecologist.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_TITLE, gynecologist.getMenuTitle(), pageId));
    openGraphMetaElementRepository
        .save(computeOpenGraphMetaElement(OG_DESCRIPTION, gynecologist.getMenuTitle(), pageId));

  }

  public static void createTechnics(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

    Page techniques = new Page();
    techniques.setMenuTitle("Techniques");
    techniques.setName("techniques");
    techniques = pageRepository.save(techniques);
    String pageId = String.valueOf(techniques.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + techniques.getName());
    menu.setLabel(techniques.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(5);
    menu.setTitle(techniques.getMenuTitle());

    menuRepository.save(menu);

    MetaElement language = computeLanguage(Locale.FRANCE, pageId);
    metaElementRepository.save(language);

    MetaElement viewport = computeViewPort(pageId);
    metaElementRepository.save(viewport);

    metaElementRepository.save(computeMetaElement(TITLE, techniques.getMenuTitle(), pageId));
    metaElementRepository.save(computeMetaElement(DESCRIPTION, techniques.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_TITLE, techniques.getMenuTitle(), pageId));
    openGraphMetaElementRepository.save(computeOpenGraphMetaElement(OG_DESCRIPTION, techniques.getMenuTitle(), pageId));

  }

  static MetaElement computeLanguage(Locale locale, String pageId) {
    return computeMetaElement(LANGUAGE, locale.getLanguage(), pageId);
  }

  static MetaElement computeViewPort(String pageId) {
    return computeMetaElement(VIEWPORT, WIDTH_DEVICE_WIDTH_INITIAL_SCALE_1_MAXIMUM_SCALE_1_USER_SCALABLE_NO, pageId);
  }

  static MetaElement computeMetaElement(String name, String content, String pageId) {
    MetaElement metaElement = new MetaElement();
    metaElement.setName(name);
    metaElement.setContent(content);
    metaElement.setPageId(pageId);
    return metaElement;

  }

  static OpenGraphMetaElement computeOpenGraphMetaElement(String property, String content, String pageId) {
    OpenGraphMetaElement metaElement = new OpenGraphMetaElement();
    metaElement.setProperty(property);
    metaElement.setContent(content);
    metaElement.setPageId(pageId);
    return metaElement;

  }

}
