package cmpl.web.carousel;

public interface CarouselTranslator {

  CarouselDTO fromCreateFormToDTO(CarouselCreateForm form);

  CarouselItemDTO fromCreateFormToDTO(CarouselItemCreateForm form);

  CarouselResponse fromDTOToResponse(CarouselDTO dto);

  CarouselItemResponse fromDTOToResponse(CarouselItemDTO dto);
}
