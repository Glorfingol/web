package cmpl.web;

import java.util.Locale;

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
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {
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
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

  }

  public static void createAppointment(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

    Page appointment = new Page();
    appointment.setMenuTitle("Prendre rendez-vous");
    appointment.setName("rendez-vous");
    appointment
        .setBody("<a href='https://www.doctolib.fr/cabinet-medical/aulnay-sous-bois/centre-medical-paul-langevin' target='_blank'><img class='img-fluid mx-auto d-block' src='../../img/doctolib.jpg' alt='Prendre rendez-vous via Doctolib' width='1228' height='1464'/></a>");
    appointment = pageRepository.save(appointment);
    String pageId = String.valueOf(appointment.getId());

    Menu menu = new Menu();
    menu.setHref("/" + appointment.getName());
    menu.setLabel(appointment.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(1);
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

  }

  public static void createCenter(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

  }

  public static void createPrices(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

  }

  public static void createContact(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

  }

  public static void createMedicalCare(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

  }

  public static void createGynecologist(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

  }

  public static void createTechnics(PageRepository pageRepository, MenuRepository menuRepository,
      MetaElementRepository metaElementRepository, OpenGraphMetaElementRepository openGraphMetaElementRepository) {

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
