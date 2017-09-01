package cmpl.web.carousel;

import java.io.File;
import java.util.List;

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

  /**
   * Transofme la liste d'images en elements de carousel
   * 
   * @param pageId, l'id de la page
   * @return
   */
  List<CarouselDTO> computeCarouselsForPage(String pageId);

}
