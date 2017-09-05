package cmpl.web.file;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.core.context.ContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class FileConfigurationTest {

  @Mock
  private ImageConverterService imageConverterService;

  @Mock
  private ContextHolder contextHolder;

  @Spy
  FileConfiguration configuration;

  @Test
  public void testImageService() throws Exception {
    ImageService result = configuration.imageService(contextHolder, imageConverterService);

    Assert.assertEquals(ImageServiceImpl.class, result.getClass());
  }

  @Test
  public void testImageConverterService() throws Exception {
    ImageConverterService result = configuration.imageConverterService();

    Assert.assertEquals(ImageConverterServiceImpl.class, result.getClass());
  }

}
