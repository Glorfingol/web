package cmpl.web.carousel;

public class CarouselTranslatorImpl implements CarouselTranslator {

  @Override
  public CarouselDTO fromCreateFormToDTO(CarouselCreateForm form) {
    CarouselDTO dto = new CarouselDTO();
    dto.setName(form.getName());
    dto.setPageId(form.getPageId());
    return dto;
  }

  @Override
  public CarouselResponse fromDTOToResponse(CarouselDTO dto) {
    CarouselResponse response = new CarouselResponse();
    response.setCarousel(dto);
    return response;
  }

  @Override
  public CarouselItemDTO fromCreateFormToDTO(CarouselItemCreateForm form) {
    CarouselItemDTO dto = new CarouselItemDTO();
    dto.setCarouselId(form.getCarouselId());
    dto.setOrderInCarousel(form.getOrderInCarousel());
    return dto;
  }

  @Override
  public CarouselItemResponse fromDTOToResponse(CarouselItemDTO dto) {
    CarouselItemResponse response = new CarouselItemResponse();
    response.setItem(dto);
    return response;
  }

}
