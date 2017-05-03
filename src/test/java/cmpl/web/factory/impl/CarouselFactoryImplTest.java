package cmpl.web.factory.impl;

import java.io.File;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

import cmpl.web.model.carousel.CarouselItem;

@RunWith(MockitoJUnitRunner.class)
public class CarouselFactoryImplTest {

  @InjectMocks
  @Spy
  private CarouselFactoryImpl carouselFactory;

  @Test
  public void testComputeImageAlt() throws Exception {

    String basePath = "plop\\";
    String alt = "someFileName";
    String extension = ".png";
    File testFile = new File(basePath + alt + extension);
    String result = carouselFactory.computeImageAlt(testFile);

    Assert.assertEquals(alt, result);
  }

  @Test
  public void testComputeImageSrc() throws Exception {

    String basePath = "test\\img\\";
    String alt = "someFileName";
    String extension = ".png";
    File testFile = new File(basePath + alt + extension);

    String result = carouselFactory.computeImageSrc(testFile);

    Assert.assertEquals("img\\" + alt + extension, result);
  }

  @Test
  public void testCanExploitImageFile_True() throws Exception {

    String path = "src/test/resources/img/logo.jpg";
    File testFile = new File(path);

    boolean result = carouselFactory.canExploitImageFile(testFile);

    Assert.assertTrue(result);
  }

  @Test
  public void testCanExploitImageFile_False_Not_Exists() throws Exception {

    String path = "toto.jpg";
    File testFile = new File(path);

    boolean result = carouselFactory.canExploitImageFile(testFile);

    Assert.assertFalse(result);
  }

  @Test
  public void testCanExploitImageFile_False_Not_File() throws Exception {

    String path = "src/test/resources/img";
    File testFile = new File(path);

    boolean result = carouselFactory.canExploitImageFile(testFile);

    Assert.assertFalse(result);
  }

  @Test
  public void testComputeCarouselItem() throws Exception {
    String src = "someSrc";
    String alt = "someAlt";

    BDDMockito.doReturn(src).when(carouselFactory).computeImageSrc(Mockito.any(File.class));
    BDDMockito.doReturn(alt).when(carouselFactory).computeImageAlt(Mockito.any(File.class));

    CarouselItem result = carouselFactory.computeCarouselItem(new File("someFile.png"));

    Assert.assertEquals(src, result.getSrc());
    Assert.assertEquals(alt, result.getAlt());
  }

  @Test
  public void testComputeCarouselItems_With_Items() throws Exception {

    CarouselItem item1 = new CarouselItem();
    item1.setAlt("someAlt1");
    item1.setSrc("someSrc1");

    CarouselItem item2 = new CarouselItem();
    item2.setAlt("someAlt1");
    item2.setSrc("someSrc1");

    File file1 = new File("someFile1");
    File file2 = new File("someFile2");

    BDDMockito.doReturn(true).when(carouselFactory).canExploitImageFile(Mockito.eq(file1));
    BDDMockito.doReturn(true).when(carouselFactory).canExploitImageFile(Mockito.eq(file2));
    BDDMockito.doReturn(item1).when(carouselFactory).computeCarouselItem(Mockito.eq(file1));
    BDDMockito.doReturn(item2).when(carouselFactory).computeCarouselItem(Mockito.eq(file2));

    List<CarouselItem> result = carouselFactory.computeCarouselItems(Lists.newArrayList(file1, file2));

    Assert.assertTrue(result.size() == 2);
    Assert.assertEquals(result.get(0), item1);
    Assert.assertEquals(result.get(1), item2);
  }

  @Test
  public void testComputeCarouselItems_With_One_Item_Valid() throws Exception {

    CarouselItem item1 = new CarouselItem();
    item1.setAlt("someAlt1");
    item1.setSrc("someSrc1");

    CarouselItem item2 = new CarouselItem();
    item2.setAlt("someAlt1");
    item2.setSrc("someSrc1");

    File file1 = new File("someFile1");
    File file2 = new File("someFile2");

    BDDMockito.doReturn(true).when(carouselFactory).canExploitImageFile(Mockito.eq(file1));
    BDDMockito.doReturn(false).when(carouselFactory).canExploitImageFile(Mockito.eq(file2));
    BDDMockito.doReturn(item1).when(carouselFactory).computeCarouselItem(Mockito.eq(file1));

    List<CarouselItem> result = carouselFactory.computeCarouselItems(Lists.newArrayList(file1, file2));

    Assert.assertTrue(result.size() == 1);
    Assert.assertEquals(result.get(0), item1);
    Mockito.verify(carouselFactory, Mockito.times(0)).computeCarouselItem(Mockito.eq(file2));
  }

  @Test
  public void testComputeCarouselItems_No_Item_Valid() throws Exception {

    CarouselItem item1 = new CarouselItem();
    item1.setAlt("someAlt1");
    item1.setSrc("someSrc1");

    CarouselItem item2 = new CarouselItem();
    item2.setAlt("someAlt1");
    item2.setSrc("someSrc1");

    File file1 = new File("someFile1");
    File file2 = new File("someFile2");

    BDDMockito.doReturn(false).when(carouselFactory).canExploitImageFile(Mockito.eq(file1));
    BDDMockito.doReturn(false).when(carouselFactory).canExploitImageFile(Mockito.eq(file2));

    List<CarouselItem> result = carouselFactory.computeCarouselItems(Lists.newArrayList(file1, file2));

    Assert.assertTrue(CollectionUtils.isEmpty(result));
    Mockito.verify(carouselFactory, Mockito.times(0)).computeCarouselItem(Mockito.eq(file1));
    Mockito.verify(carouselFactory, Mockito.times(0)).computeCarouselItem(Mockito.eq(file2));
  }
}
