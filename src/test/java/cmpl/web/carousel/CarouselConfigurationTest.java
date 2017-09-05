package cmpl.web.carousel;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarouselConfigurationTest {

  @Mock
  private CarouselService carouselService;

  @Spy
  CarouselConfiguration configuration;

  @Test
  public void testCarouselFactory() throws Exception {
    CarouselFactory result = configuration.carouselFactory(carouselService);

    Assert.assertEquals(CarouselFactoryImpl.class, result.getClass());
  }

}
