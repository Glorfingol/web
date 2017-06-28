package cmpl.web.factory.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cmpl.web.factory.CarouselFactory;
import cmpl.web.model.carousel.CarouselItem;

/**
 * Implementation de l'interface de factory pour creer les elements d'un carousel a partir d'images
 * 
 * @author Louis
 *
 */
public class CarouselFactoryImpl implements CarouselFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(CarouselFactoryImpl.class);

  private CarouselFactoryImpl() {

  }

  /**
   * Constructeur static pour la configuration
   * 
   * @return
   */
  public static CarouselFactoryImpl fromVoid() {
    return new CarouselFactoryImpl();
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

}
