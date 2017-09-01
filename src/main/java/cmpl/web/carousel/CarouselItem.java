package cmpl.web.carousel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cmpl.web.core.model.BaseEntity;

/**
 * Object pour les carousel d'image
 * 
 * @author Louis
 *
 */
@Entity(name = "carouselItem")
@Table(name = "carouse_item")
public class CarouselItem extends BaseEntity {

  @Column(name = "src")
  private String src;
  @Column(name = "alt")
  private String alt;
  @Column(name = "carousel_id")
  private String carouselId;
  @Column(name = "order_in_carousel")
  private int orderInCarousel;

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

  public String getCarouselId() {
    return carouselId;
  }

  public void setCarouselId(String carouselId) {
    this.carouselId = carouselId;
  }

  public int getOrderInCarousel() {
    return orderInCarousel;
  }

  public void setOrderInCarousel(int orderInCarousel) {
    this.orderInCarousel = orderInCarousel;
  }

}
