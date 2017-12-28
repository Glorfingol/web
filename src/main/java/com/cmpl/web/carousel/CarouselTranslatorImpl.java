package com.cmpl.web.carousel;

public class CarouselTranslatorImpl implements CarouselTranslator {

  @Override
  public CarouselDTO fromCreateFormToDTO(CarouselCreateForm form) {
    return new CarouselDTOBuilder().name(form.getName()).pageId(form.getPageId()).build();
  }

  @Override
  public CarouselResponse fromDTOToResponse(CarouselDTO dto) {
    return new CarouselResponseBuilder().carousel(dto).build();
  }

  @Override
  public CarouselItemDTO fromCreateFormToDTO(CarouselItemCreateForm form) {
    return new CarouselItemDTOBuilder().carouselId(form.getCarouselId()).orderInCarousel(form.getOrderInCarousel())
        .build();
  }

  @Override
  public CarouselItemResponse fromDTOToResponse(CarouselItemDTO dto) {
    return new CarouselItemResponseBuilder().item(dto).build();
  }

}
