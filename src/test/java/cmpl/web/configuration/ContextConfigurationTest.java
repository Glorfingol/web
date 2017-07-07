package cmpl.web.configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.model.context.ContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class ContextConfigurationTest {

  @Spy
  private ContextConfiguration configuration;

  @Test
  public void testContextHolder() throws Exception {

    String fileBasePath = "someFileBasePath";
    String imageDisplaySrc = "someDisplaySrc";

    configuration.fileBasePath = fileBasePath;
    configuration.imageDisplaySrc = imageDisplaySrc;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    ContextHolder holder = configuration.contextHolder();

    Date dateToFormat = new Date();
    Assert.assertEquals(dateFormat.format(dateToFormat), holder.getDateFormat().format(dateToFormat));
    Assert.assertEquals(fileBasePath, holder.getImageFileSrc());
    Assert.assertEquals(imageDisplaySrc, holder.getImageDisplaySrc());

  }
}
