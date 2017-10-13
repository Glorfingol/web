package cmpl.web.carousel;

import java.time.LocalDate;
import java.util.List;

public class CarouselUpdateForm {

  private String name;
  private String pageId;
  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;
  private List<CarouselItemDTO> items;

  public CarouselUpdateForm() {
  }

  public CarouselUpdateForm(CarouselDTO carousel) {
    this.name = carousel.getName();
    this.pageId = carousel.getPageId();
    this.id = carousel.getId();
    this.creationDate = carousel.getCreationDate();
    this.modificationDate = carousel.getModificationDate();
    this.items = carousel.getCarouselItems();
  }

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDate getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
  }

  public List<CarouselItemDTO> getItems() {
    return items;
  }

  public void setItems(List<CarouselItemDTO> items) {
    this.items = items;
  }

}
