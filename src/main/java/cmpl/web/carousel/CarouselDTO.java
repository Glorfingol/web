package cmpl.web.carousel;

import java.util.List;

import cmpl.web.core.model.BaseDTO;

public class CarouselDTO extends BaseDTO {

  private String name;
  private String pageId;
  private List<CarouselItemDTO> carouselItems;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public List<CarouselItemDTO> getCarouselItems() {
    return carouselItems;
  }

  public void setCarouselItems(List<CarouselItemDTO> carouselItems) {
    this.carouselItems = carouselItems;
  }

}