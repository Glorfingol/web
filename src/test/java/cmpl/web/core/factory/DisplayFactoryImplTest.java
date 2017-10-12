package cmpl.web.core.factory;

import java.io.File;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

import cmpl.web.carousel.CarouselFactory;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSource;
import cmpl.web.meta.MetaElementFactory;

@RunWith(MockitoJUnitRunner.class)
public class DisplayFactoryImplTest {

  @Mock
  private MenuFactory menuFactory;
  @Mock
  private MetaElementFactory metaElementFactory;
  @Mock
  private WebMessageSource messageSource;
  @Mock
  private CarouselFactory carouseFactory;

  @InjectMocks
  @Spy
  private DisplayFactoryImpl displayFactory;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeCarouselImagesFiles() throws Exception {
    String srcs = "static/img/logo/logo.jpg;static/img/logo/logoSmall.jpg";

    BDDMockito.doReturn(srcs).when(displayFactory).getI18nValue(Mockito.anyString(), Mockito.eq(locale));
    List<File> result = displayFactory.computeCarouselImagesFiles(locale);

    Assert.assertFalse(CollectionUtils.isEmpty(result));

    for (File file : result) {
      Assert.assertTrue(file.exists());
    }
  }

}
