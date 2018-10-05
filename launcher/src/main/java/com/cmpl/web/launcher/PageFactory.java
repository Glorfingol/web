package com.cmpl.web.launcher;

import com.cmpl.web.core.carousel.CarouselRepository;
import com.cmpl.web.core.carousel.item.CarouselItemRepository;
import com.cmpl.web.core.media.MediaBuilder;
import com.cmpl.web.core.media.MediaRepository;
import com.cmpl.web.core.models.Carousel;
import com.cmpl.web.core.models.CarouselItem;
import com.cmpl.web.core.models.Media;
import com.cmpl.web.core.models.Page;
import com.cmpl.web.core.models.Widget;
import com.cmpl.web.core.models.WidgetPage;
import com.cmpl.web.core.page.PageRepository;
import com.cmpl.web.core.widget.WidgetBuilder;
import com.cmpl.web.core.widget.WidgetRepository;
import com.cmpl.web.core.widget.page.WidgetPageBuilder;
import com.cmpl.web.core.widget.page.WidgetPageRepository;

public class PageFactory {

  public static void createPages(PageRepository pageRepository,

    CarouselRepository carouselRepository, CarouselItemRepository carouselItemRepository,
    MediaRepository mediaRepository, WidgetRepository widgetRepository,
    WidgetPageRepository widgetPageRepository) {
    createIndex(pageRepository, carouselRepository, carouselItemRepository,
      mediaRepository,
      widgetRepository, widgetPageRepository);
    createActualites(pageRepository, widgetRepository, widgetPageRepository);
    createAppointment(pageRepository);
    createCenter(pageRepository);
    createContact(pageRepository);
    createMedicalCare(pageRepository);

  }

  public static void createIndex(PageRepository pageRepository,

    CarouselRepository carouselRepository, CarouselItemRepository carouselItemRepository,
    MediaRepository mediaRepository, WidgetRepository widgetRepository,
    WidgetPageRepository widgetPageRepository) {

    Page index = new Page();
    index.setMenuTitle("Accueil");
    index.setName("accueil");
    index.setHref("accueil");
    index = pageRepository.save(index);
    String pageId = String.valueOf(index.getId());

    Carousel carouselHome = new Carousel();
    carouselHome.setName("home");

    carouselHome = carouselRepository.save(carouselHome);
    String carouselId = String.valueOf(carouselHome.getId());

    Media firstMedia = MediaBuilder.create().build();
    firstMedia.setContentType("image/jpg");
    firstMedia.setExtension(".jpg");
    firstMedia.setName("epilation_verso.jpg");
    firstMedia.setSrc("/public/medias/epilation_verso.jpg");
    firstMedia.setSize(114688l);

    firstMedia = mediaRepository.save(firstMedia);

    CarouselItem firstImage = new CarouselItem();
    firstImage.setMediaId(String.valueOf(firstMedia.getId()));
    firstImage.setCarouselId(carouselId);
    firstImage.setOrderInCarousel(1);

    Media secondMedia = MediaBuilder.create().build();
    secondMedia.setContentType("image/jpg");
    secondMedia.setExtension(".jpg");
    secondMedia.setName("epilation_recto.jpg");
    secondMedia.setSrc("/public/medias/epilation_recto.jpg");
    secondMedia.setSize(61440l);

    secondMedia = mediaRepository.save(secondMedia);

    carouselItemRepository.save(firstImage);

    CarouselItem secondImage = new CarouselItem();
    secondImage.setMediaId(String.valueOf(secondMedia.getId()));
    secondImage.setOrderInCarousel(2);
    secondImage.setCarouselId(carouselId);

    carouselItemRepository.save(secondImage);

    Widget widgetCarouselHome = WidgetBuilder.create().type("CAROUSEL").name("carousel_home")
      .entityId(carouselId)
      .asynchronous(true).build();
    widgetCarouselHome = widgetRepository.save(widgetCarouselHome);
    WidgetPage widgetPage = WidgetPageBuilder.create()
      .widgetId(widgetCarouselHome.getId())
      .pageId(Long.parseLong(pageId)).build();
    widgetPageRepository.save(widgetPage);

  }

  public static void createActualites(PageRepository pageRepository,

    WidgetRepository widgetRepository, WidgetPageRepository widgetPageRepository) {

    Page actualites = new Page();
    actualites.setMenuTitle("Actualites");
    actualites.setName("actualites");
    actualites.setHref("actualites");
    actualites = pageRepository.save(actualites);

    String pageId = String.valueOf(actualites.getId());

    Widget blog = WidgetBuilder.create().name("blog").asynchronous(true).type("BLOG").build();
    blog = widgetRepository.save(blog);
    WidgetPage widgetPage = WidgetPageBuilder.create().pageId(Long.parseLong(pageId))
      .widgetId(blog.getId()).build();
    widgetPageRepository.save(widgetPage);


  }

  public static void createAppointment(PageRepository pageRepository) {

    Page appointment = new Page();
    appointment.setMenuTitle("Prendre rendez-vous");
    appointment.setName("rendez_vous");
    appointment.setHref("rendez-vous");
    appointment = pageRepository.save(appointment);
    String pageId = String.valueOf(appointment.getId());


  }

  public static void createCenter(PageRepository pageRepository) {
    Page center = new Page();
    center.setMenuTitle("Le centre");
    center.setName("centre_medical");
    center.setHref("le-centre");
    center = pageRepository.save(center);
    String pageId = String.valueOf(center.getId());


  }

  public static void createContact(PageRepository pageRepository) {

    Page contact = new Page();
    contact.setMenuTitle("Contact");
    contact.setName("contact");
    contact.setHref("contact");
    contact = pageRepository.save(contact);
    String pageId = String.valueOf(contact.getId());


  }

  public static void createMedicalCare(PageRepository pageRepository
  ) {

    Page medicalCare = new Page();
    medicalCare.setMenuTitle("Soins medicaux");
    medicalCare.setName("soins_medicaux");
    medicalCare.setHref("soins-medicaux");
    medicalCare = pageRepository.save(medicalCare);
    String pageId = String.valueOf(medicalCare.getId());

  }

}
