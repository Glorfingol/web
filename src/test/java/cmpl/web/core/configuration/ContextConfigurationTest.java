package cmpl.web.core.configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.core.configuration.ContextConfiguration;
import cmpl.web.core.context.ContextHolder;

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

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");

    ContextHolder holder = configuration.contextHolder();

    LocalDate dateToFormat = LocalDate.now();
    Assert.assertEquals(dateFormat.format(dateToFormat), holder.getDateFormat().format(dateToFormat));
    Assert.assertEquals(fileBasePath, holder.getImageFileSrc());
    Assert.assertEquals(imageDisplaySrc, holder.getImageDisplaySrc());

  }
}
