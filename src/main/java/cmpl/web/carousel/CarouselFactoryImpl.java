package cmpl.web.carousel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation de l'interface de factory pour creer les elements d'un carousel a partir d'images
 * 
 * @author Louis
 *
 */
public class CarouselFactoryImpl implements CarouselFactory {

  private final CarouselService carouselService;

  protected static final Logger LOGGER = LoggerFactory.getLogger(CarouselFactoryImpl.class);

  private CarouselFactoryImpl(CarouselService carouselService) {
    this.carouselService = carouselService;

  }

  /**
   * Constructeur static pour la configuration
   * 
   * @return
   */
  public static CarouselFactoryImpl fromService(CarouselService carouselService) {
    return new CarouselFactoryImpl(carouselService);
  }

  @Override
  public List<CarouselItem> computeCarouselItems(List<File> images) {
    List<CarouselItem> carouselItems = new ArrayList<>();
    for (File image : images) {
      carouselItems.add(computeCarouselItem(image));
    }
    return carouselItems;
  }

  CarouselItem computeCarouselItem(File image) {
    CarouselItem carouselItem = new CarouselItem();
    carouselItem.setSrc(computeImageSrc(image));
    carouselItem.setAlt(computeImageAlt(image));
    return carouselItem;
  }

  String computeImageSrc(File file) {
    String filePath = file.getPath();
    int firstIndex = filePath.indexOf("img");
    filePath = filePath.substring(firstIndex, filePath.length());
    return filePath;
  }

  String computeImageAlt(File file) {
    String filePath = file.getPath();
    int firstIndex = filePath.lastIndexOf('\\');
    int lastIndex = filePath.indexOf('.');

    return filePath.substring(firstIndex + 1, lastIndex);
  }

  @Override
  public List<CarouselDTO> computeCarouselsForPage(String pageId) {
    return carouselService.findByPageId(pageId);
  }

}
