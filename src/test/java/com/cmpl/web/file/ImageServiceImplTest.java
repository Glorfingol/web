package com.cmpl.web.file;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.model.BaseException;

;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private ImageConverterService imageConverterService;

  @Mock
  private ContextHolder contextHolder;

  @InjectMocks
  @Spy
  private ImageServiceImpl service;

  @Before
  public void setUp() {
    File existingFile = new File("test");
    if (existingFile.exists()) {
      existingFile.delete();
    }

    File folderMain = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites");
    if (folderMain.exists()) {
      folderMain.delete();
    }

    File subFolder = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites" + File.separator + "666");
    if (subFolder.exists()) {
      subFolder.delete();
    }

    service = new ImageServiceImpl(contextHolder, imageConverterService);
    service = BDDMockito.spy(service);

    BDDMockito
        .doReturn(
            "src" + File.separator + "test" + File.separator + "resources" + File.separator + "img" + File.separator
                + "actualites" + File.separator + "").when(contextHolder).getImageFileSrc();

  }

  @Test
  public void testDeleteFileIfExistsAndReturnResult_File_Existed() throws Exception {
    File existingFile = new File("test");
    existingFile.createNewFile();

    boolean result = service.deleteFileIfExistsAndReturnResult(existingFile);

    Assert.assertTrue(result);
  }

  @Test
  public void testDeleteFileIfExistsAndReturnResult_File_Did_Not_Exist() throws Exception {
    File existingFile = new File("test");

    boolean result = service.deleteFileIfExistsAndReturnResult(existingFile);

    Assert.assertFalse(result);
  }

  @Test
  public void testCreateNewFile_Created() throws Exception {
    File fileTest = new File("test");

    service.createNewFile("SomeId", fileTest);

    Assert.assertTrue(fileTest.exists());

    fileTest.delete();
  }

  @Test
  public void testCreateNewFile_Error() throws Exception {
    File fileTest = new File("plodfdfdf/test");

    exception.expect(BaseException.class);
    service.createNewFile("SomeId", fileTest);

    Assert.assertFalse(fileTest.exists());
  }

  @Test
  public void testInstantiateFileFromPath() throws Exception {
    Path test = Paths.get("plopl/test");

    File result = service.instantiateFileFromPath(test);

    String filePath = result.getPath();
    int firstIndex = filePath.indexOf("plopl");
    filePath = filePath.substring(firstIndex, filePath.length());

    Assert.assertEquals(test.toFile().getPath(), filePath);
  }

  @Test
  public void testComputeFolderPath() throws Exception {
    String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites" + File.separator + "666";

    Path result = service.computeFolderPath("666");

    Assert.assertEquals(path, result.toString());
  }

  @Test
  public void testComputeMainFolderPath() throws Exception {
    String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites";

    Path result = service.computeMainFolderPath();

    Assert.assertEquals(path, result.toString());
  }

  @Test
  public void testComputePath() throws Exception {
    String format = "jpg";
    String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites" + File.separator + "666" + File.separator + "image.jpg";

    Path result = service.computePath("666", format);

    Assert.assertEquals(path, result.toString());
  }

  @Test
  public void testCreateMainFolderIfRequired_required() throws Exception {
    Path path = Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites");

    BDDMockito.doReturn(path).when(service).computeMainFolderPath();

    boolean result = service.createMainFolderIfRequired();

    File file = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites");
    Assert.assertTrue(file.isDirectory());
    Assert.assertTrue(result);

  }

  @Test
  public void testCreateMainFolderIfRequired_not_required() throws Exception {
    File directory = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites");
    directory.mkdir();

    Path path = Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites");

    BDDMockito.doReturn(path).when(service).computeMainFolderPath();

    boolean result = service.createMainFolderIfRequired();
    Assert.assertFalse(result);

  }

  @Test
  public void testCreateSubFolderIfRequired() throws Exception {
    File directory = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites");
    directory.mkdir();
    Path path = Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites" + File.separator + "666");

    BDDMockito.doReturn(path).when(service).computeFolderPath(BDDMockito.anyString());

    boolean result = service.createSubFolderIfRequired("666");

    File file = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites" + File.separator + "666");
    Assert.assertTrue(file.isDirectory());
    Assert.assertTrue(result);

  }

  @Test
  public void testCreateSubFolderIfRequired_not_required() throws Exception {
    File directory = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites");
    directory.mkdir();
    directory = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites" + File.separator + "666");
    directory.mkdir();

    Path path = Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "img"
        + File.separator + "actualites" + File.separator + "666");

    BDDMockito.doReturn(path).when(service).computeFolderPath(BDDMockito.anyString());

    boolean result = service.createSubFolderIfRequired("666");

    Assert.assertFalse(result);

  }

  @Test
  public void testCreateFoldersIfRequired() throws Exception {
    BDDMockito.doReturn(true).when(service).createMainFolderIfRequired();
    BDDMockito.doReturn(true).when(service).createSubFolderIfRequired(BDDMockito.anyString());

    service.createFoldersIfRequired("666");

    BDDMockito.verify(service, BDDMockito.times(1)).createMainFolderIfRequired();
    BDDMockito.verify(service, BDDMockito.times(1)).createSubFolderIfRequired(BDDMockito.eq("666"));
  }

  @Test
  public void testWriteBufferedImageToFile() throws Exception {
    BufferedImage bufferedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);

    File fileToSave = new File("someFile");

    fileToSave = service.writeBufferedImageToFile(bufferedImage, fileToSave, "png");

    BufferedReader br = new BufferedReader(new FileReader(fileToSave));
    Assert.assertFalse(br.readLine() == null);
    br.close();
  }

  @Test
  public void testWriteBufferedImageToFile_exception() throws Exception {
    BufferedImage bufferedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);

    exception.expect(BaseException.class);
    service.writeBufferedImageToFile(bufferedImage, new File(""), "png");
  }

  @Test
  public void testReadBytesToBufferedImage() throws Exception {

    String path = "src/test/resources/img/base64Image.txt";
    byte[] encoded = Files.readAllBytes(Paths.get(path));

    service.readBytesToBufferedImage("666", encoded);

  }

  @Test
  public void testReadBytesToBufferedImage_Exception() throws Exception {

    exception.expect(BaseException.class);
    service.readBytesToBufferedImage("666", null);

  }

  @Test
  public void testConvertBase64ContentToBytes() throws Exception {
    String base64 = "png,lolo";

    byte[] jpg = new byte[]{1};

    BDDMockito.doReturn(jpg).when(imageConverterService).getImageByteArray(BDDMockito.eq(base64));
    byte[] result = service.convertBase64ContentToBytes("666", base64);

    Assert.assertEquals(jpg, result);

    BDDMockito.verify(imageConverterService, BDDMockito.times(1)).getImageByteArray(BDDMockito.eq(base64));
  }

  @Test
  public void testConvertBase64ContentToBytes_Exception() throws Exception {

    exception.expect(BaseException.class);
    BDDMockito.doThrow(new IOException()).when(imageConverterService).getImageByteArray(BDDMockito.anyString());
    service.convertBase64ContentToBytes("666", null);

    BDDMockito.verify(imageConverterService, BDDMockito.times(1)).getImageByteArray(BDDMockito.anyString());
  }

  @Test
  public void testSaveFileOnSystem() throws Exception {
    String format = "png";
    File file = new File("someFile");
    Path path = Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator
        + "actualites" + File.separator + "666");
    byte[] data = new byte[]{1};
    BufferedImage bufferedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);

    BDDMockito.doNothing().when(service).createFoldersIfRequired(BDDMockito.anyString());
    BDDMockito.doNothing().when(service).createFoldersIfRequired(BDDMockito.anyString());
    BDDMockito.doReturn(format).when(service).extractFormatFromBase64(BDDMockito.anyString());
    BDDMockito.doReturn(path).when(service).computePath(BDDMockito.anyString(), BDDMockito.anyString());
    BDDMockito.doReturn(data).when(service).convertBase64ContentToBytes(BDDMockito.anyString(), BDDMockito.anyString());
    BDDMockito.doReturn(bufferedImage).when(service)
        .readBytesToBufferedImage(BDDMockito.anyString(), BDDMockito.any(byte[].class));
    BDDMockito.doReturn(file).when(service).instantiateFileFromPath(BDDMockito.any(Path.class));
    BDDMockito.doReturn(true).when(service).deleteFileIfExistsAndReturnResult(BDDMockito.any(File.class));
    BDDMockito.doNothing().when(service).createNewFile(BDDMockito.anyString(), BDDMockito.any(File.class));
    BDDMockito
        .doReturn(file)
        .when(service)
        .writeBufferedImageToFile(BDDMockito.any(BufferedImage.class), BDDMockito.any(File.class),
            BDDMockito.anyString());

    File result = service.saveFileOnSystem("666", "someBase64");

    Assert.assertEquals(file, result);

    BDDMockito.verify(service, BDDMockito.times(1)).createFoldersIfRequired(BDDMockito.anyString());
    BDDMockito.verify(service, BDDMockito.times(1)).extractFormatFromBase64(BDDMockito.anyString());
    BDDMockito.verify(service, BDDMockito.times(1)).computePath(BDDMockito.anyString(), BDDMockito.anyString());
    BDDMockito.verify(service, BDDMockito.times(1)).convertBase64ContentToBytes(BDDMockito.anyString(),
        BDDMockito.anyString());
    BDDMockito.verify(service, BDDMockito.times(1)).readBytesToBufferedImage(BDDMockito.anyString(),
        BDDMockito.any(byte[].class));
    BDDMockito.verify(service, BDDMockito.times(1)).instantiateFileFromPath(BDDMockito.any(Path.class));
    BDDMockito.verify(service, BDDMockito.times(1)).deleteFileIfExistsAndReturnResult(BDDMockito.any(File.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createNewFile(BDDMockito.anyString(), BDDMockito.any(File.class));
    BDDMockito.verify(service, BDDMockito.times(1)).writeBufferedImageToFile(BDDMockito.any(BufferedImage.class),
        BDDMockito.any(File.class), BDDMockito.anyString());

    file.delete();

  }

  @Test
  public void testExtractFormatFromBase64() throws Exception {
    String base64 = "data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";
    String result = service.extractFormatFromBase64(base64);

    Assert.assertEquals("png", result);
  }

}
