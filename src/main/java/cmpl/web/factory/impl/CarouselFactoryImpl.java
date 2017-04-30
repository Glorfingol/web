package cmpl.web.factory.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cmpl.web.factory.CarouselFactory;
import cmpl.web.model.carousel.CarouselItem;

public class CarouselFactoryImpl implements CarouselFactory {

  private CarouselFactoryImpl() {

  }

  public static CarouselFactoryImpl fromVoid() {
    return new CarouselFactoryImpl();
  }

  @Override
  public List<CarouselItem> computeCarouselItems(List<File> images) {
    List<CarouselItem> carouselItems = new ArrayList<>();
    for (File image : images) {
      if (canExploitImageFile(image)) {
        carouselItems.add(computeCarouselItem(image));
      }
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
    int firstIndex = filePath.lastIndexOf("\\");
    int lastIndex = filePath.indexOf(".");

    return filePath.substring(firstIndex + 1, lastIndex);
  }

  boolean canExploitImageFile(File file) {
    if (!file.exists()) {
      return false;
    }
    return file.isFile();
  }

}
