package cmpl.web.carousel;

import java.util.Locale;

import cmpl.web.core.model.Error;
import cmpl.web.media.MediaDTO;
import cmpl.web.media.MediaService;

public class CarouselDispatcherImpl implements CarouselDispatcher {

  private final CarouselService carouselService;
  private final CarouselItemService carouselItemService;
  private final MediaService mediaService;
  private final CarouselTranslator translator;
  private final CarouselValidator validator;

  public CarouselDispatcherImpl(CarouselService carouselService, CarouselItemService carouselItemService,
      MediaService mediaService, CarouselTranslator carouselTransaltor, CarouselValidator carouselValidator) {
    this.carouselItemService = carouselItemService;
    this.carouselService = carouselService;
    this.translator = carouselTransaltor;
    this.mediaService = mediaService;
    this.validator = carouselValidator;
  }

  @Override
  public CarouselResponse createEntity(CarouselCreateForm form, Locale locale) {
    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      CarouselResponse response = new CarouselResponse();
      response.setError(error);
      return response;
    }

    CarouselDTO carouselToCreate = translator.fromCreateFormToDTO(form);
    CarouselDTO createdCarousel = carouselService.createEntity(carouselToCreate);
    return translator.fromDTOToResponse(createdCarousel);
  }

  @Override
  public CarouselResponse updateEntity(CarouselUpdateForm form, Locale locale) {
    Error error = validator.validateUpdate(form, locale);

    if (error != null) {
      CarouselResponse response = new CarouselResponse();
      response.setError(error);
      return response;
    }

    CarouselDTO carouselToUpdate = carouselService.getEntity(form.getId());
    carouselToUpdate.setName(form.getName());
    carouselToUpdate.setPageId(form.getPageId());
    carouselToUpdate.setCarouselItems(form.getItems());

    CarouselDTO updatedCarousel = carouselService.updateEntity(carouselToUpdate);

    return translator.fromDTOToResponse(updatedCarousel);
  }

  @Override
  public CarouselItemResponse createEntity(CarouselItemCreateForm form, Locale locale) {
    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      CarouselItemResponse response = new CarouselItemResponse();
      response.setError(error);
      return response;
    }

    CarouselItemDTO itemToCreate = translator.fromCreateFormToDTO(form);
    MediaDTO media = mediaService.getEntity(Long.valueOf(form.getMediaId()));
    itemToCreate.setMedia(media);
    CarouselItemDTO createdItem = carouselItemService.createEntity(itemToCreate);

    return translator.fromDTOToResponse(createdItem);
  }

}
