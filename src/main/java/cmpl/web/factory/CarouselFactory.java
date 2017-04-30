package cmpl.web.factory;

import java.io.File;
import java.util.List;

import cmpl.web.model.carousel.CarouselItem;

public interface CarouselFactory {

  List<CarouselItem> computeCarouselItems(List<File> images);

}
