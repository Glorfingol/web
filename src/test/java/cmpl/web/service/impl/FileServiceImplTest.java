package cmpl.web.service.impl;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.model.BaseException;
import cmpl.web.service.ImageConverterService;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceImplTest {

  @Mock
  private ImageConverterService imageConverterService;

  @InjectMocks
  @Spy
  private FileServiceImpl service;

  @Before
  public void setUp() {
    File existingFile = new File("test");
    if (existingFile.exists()) {
      existingFile.delete();
    }

    File folderMain = new File("C:\\test\\img\\img\\actualites");
    if (folderMain.exists()) {
      folderMain.delete();
    }

    File subFolder = new File("C:\\test\\img\\actualites\\666");
    if (subFolder.exists()) {
      subFolder.delete();
    }

    service = FileServiceImpl.fromStringAndService("C:\\test\\img\\actualites\\", imageConverterService);
    service = Mockito.spy(service);

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

    try {
      service.createNewFile("SomeId", fileTest);
      Assert.fail();
    } catch (Exception e) {
      Assert.assertEquals(BaseException.class, e.getClass());
    }

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
    String path = "C:\\test\\img\\actualites\\666";

    Path result = service.computeFolderPath("666");

    Assert.assertEquals(path, result.toString());
  }

  @Test
  public void testComputeMainFolderPath() throws Exception {
    String path = "C:\\test\\img\\actualites";

    Path result = service.computeMainFolderPath();

    Assert.assertEquals(path, result.toString());
  }

  @Test
  public void testComputePath() throws Exception {
    String format = "jpg";
    String path = "C:\\test\\img\\actualites\\666\\image.jpg";

    Path result = service.computePath("666", format);

    Assert.assertEquals(path, result.toString());
  }

  @Test
  public void testCreateMainFolderIfRequired_required() throws Exception {
    Path path = Paths.get("C:\\test\\img\\actualites");

    BDDMockito.doReturn(path).when(service).computeMainFolderPath();

    boolean result = service.createMainFolderIfRequired();

    File file = new File("C:\\test\\img\\actualites");
    Assert.assertTrue(file.isDirectory());
    Assert.assertTrue(result);

  }

  @Test
  public void testCreateMainFolderIfRequired_not_required() throws Exception {
    File directory = new File("C:\\test\\img\\actualites");
    directory.mkdir();

    Path path = Paths.get("C:\\test\\img\\actualites");

    BDDMockito.doReturn(path).when(service).computeMainFolderPath();

    boolean result = service.createMainFolderIfRequired();
    Assert.assertFalse(result);

  }

  @Test
  public void testCreateSubFolderIfRequired() throws Exception {
    File directory = new File("C:\\test\\img\\actualites");
    directory.mkdir();
    Path path = Paths.get("C:\\test\\img\\actualites\\666");

    BDDMockito.doReturn(path).when(service).computeFolderPath(Mockito.anyString());

    boolean result = service.createSubFolderIfRequired("666");

    File file = new File("C:\\test\\img\\actualites\\666");
    Assert.assertTrue(file.isDirectory());
    Assert.assertTrue(result);

  }

  @Test
  public void testCreateSubFolderIfRequired_not_required() throws Exception {
    File directory = new File("C:\\test\\img\\actualites");
    directory.mkdir();
    directory = new File("C:\\test\\img\\actualites\\666");
    directory.mkdir();

    Path path = Paths.get("C:\\test\\img\\actualites\\666");

    BDDMockito.doReturn(path).when(service).computeFolderPath(Mockito.anyString());

    boolean result = service.createSubFolderIfRequired("666");

    Assert.assertFalse(result);

  }

  @Test
  public void testCreateFoldersIfRequired() throws Exception {
    BDDMockito.doReturn(true).when(service).createMainFolderIfRequired();
    BDDMockito.doReturn(true).when(service).createSubFolderIfRequired(Mockito.anyString());

    service.createFoldersIfRequired("666");

    Mockito.verify(service, Mockito.times(1)).createMainFolderIfRequired();
    Mockito.verify(service, Mockito.times(1)).createSubFolderIfRequired(Mockito.eq("666"));
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

    try {
      service.writeBufferedImageToFile(bufferedImage, new File(""), "png");
      Assert.fail();
    } catch (Exception e) {
      Assert.assertEquals(BaseException.class, e.getClass());
    }
  }

  @Test
  public void testReadBytesToBufferedImage() throws Exception {

    String path = "src/test/resources/img/base64Image.txt";
    byte[] encoded = Files.readAllBytes(Paths.get(path));

    service.readBytesToBufferedImage("666", encoded);

  }

  @Test
  public void testReadBytesToBufferedImage_Exception() throws Exception {

    try {
      service.readBytesToBufferedImage("666", null);
      Assert.fail();
    } catch (Exception e) {
      Assert.assertEquals(BaseException.class, e.getClass());
    }

  }

  @Test
  public void testConvertBase64ContentToBytes() throws Exception {
    String base64 = "png,lolo";

    byte[] jpg = new byte[]{1};

    BDDMockito.doReturn(jpg).when(imageConverterService).getImageByteArray(Mockito.eq(base64));
    byte[] result = service.convertBase64ContentToBytes("666", base64);

    Assert.assertEquals(jpg, result);

    Mockito.verify(imageConverterService, Mockito.times(1)).getImageByteArray(Mockito.eq(base64));
  }

  @Test
  public void testConvertBase64ContentToBytes_Exception() throws Exception {

    BDDMockito.doThrow(new IOException()).when(imageConverterService).getImageByteArray(Mockito.anyString());
    try {
      service.convertBase64ContentToBytes("666", null);
      Assert.fail();
    } catch (Exception e) {
      Assert.assertEquals(BaseException.class, e.getClass());
    }

    Mockito.verify(imageConverterService, Mockito.times(1)).getImageByteArray(Mockito.anyString());
  }

  @Test
  public void testSaveFileOnSystem() throws Exception {
    String format = "png";
    File file = new File("someFile");
    Path path = Paths.get("C:\\test\\img\\actualites\\666");
    byte[] data = new byte[]{1};
    BufferedImage bufferedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);

    BDDMockito.doNothing().when(service).createFoldersIfRequired(Mockito.anyString());
    BDDMockito.doNothing().when(service).createFoldersIfRequired(Mockito.anyString());
    BDDMockito.doReturn(format).when(service).extractFormatFromBase64(Mockito.anyString());
    BDDMockito.doReturn(path).when(service).computePath(Mockito.anyString(), Mockito.anyString());
    BDDMockito.doReturn(data).when(service).convertBase64ContentToBytes(Mockito.anyString(), Mockito.anyString());
    BDDMockito.doReturn(bufferedImage).when(service).readBytesToBufferedImage(Mockito.anyString(),
        Mockito.any(byte[].class));
    BDDMockito.doReturn(file).when(service).instantiateFileFromPath(Mockito.any(Path.class));
    BDDMockito.doReturn(true).when(service).deleteFileIfExistsAndReturnResult(Mockito.any(File.class));
    BDDMockito.doNothing().when(service).createNewFile(Mockito.anyString(), Mockito.any(File.class));
    BDDMockito.doReturn(file).when(service).writeBufferedImageToFile(Mockito.any(BufferedImage.class),
        Mockito.any(File.class), Mockito.anyString());

    File result = service.saveFileOnSystem("666", "someBase64");

    Assert.assertEquals(file, result);

    Mockito.verify(service, Mockito.times(1)).createFoldersIfRequired(Mockito.anyString());
    Mockito.verify(service, Mockito.times(1)).extractFormatFromBase64(Mockito.anyString());
    Mockito.verify(service, Mockito.times(1)).computePath(Mockito.anyString(), Mockito.anyString());
    Mockito.verify(service, Mockito.times(1)).convertBase64ContentToBytes(Mockito.anyString(), Mockito.anyString());
    Mockito.verify(service, Mockito.times(1)).readBytesToBufferedImage(Mockito.anyString(), Mockito.any(byte[].class));
    Mockito.verify(service, Mockito.times(1)).instantiateFileFromPath(Mockito.any(Path.class));
    Mockito.verify(service, Mockito.times(1)).deleteFileIfExistsAndReturnResult(Mockito.any(File.class));
    Mockito.verify(service, Mockito.times(1)).createNewFile(Mockito.anyString(), Mockito.any(File.class));
    Mockito.verify(service, Mockito.times(1)).writeBufferedImageToFile(Mockito.any(BufferedImage.class),
        Mockito.any(File.class), Mockito.anyString());

    file.delete();

  }

  @Test
  public void testExtractFormatFromBase64() throws Exception {
    String base64 = "data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";
    String result = service.extractFormatFromBase64(base64);

    Assert.assertEquals("png", result);
  }

}
