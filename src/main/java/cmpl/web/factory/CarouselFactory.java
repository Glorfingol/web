package cmpl.web.factory;

import java.io.File;
import java.util.List;

import cmpl.web.model.carousel.CarouselItem;

/**
 * Interface de factory pour creer les elements d'un carousel a partir d'images
 * 
 * @author Louis
 *
 */
public interface CarouselFactory {

  /**
   * Transofme la liste d'images en elements de carousel
   * 
   * @param images
   * @return
   */
  List<CarouselItem> computeCarouselItems(List<File> images);

}
