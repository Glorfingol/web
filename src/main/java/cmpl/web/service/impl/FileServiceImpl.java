package cmpl.web.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cmpl.web.model.BaseException;
import cmpl.web.service.FileService;
import cmpl.web.service.ImageConverterService;

public class FileServiceImpl implements FileService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageConverterServiceImpl.class);
  private static final String FILE_BASE_PATH = "src/main/resources/static/img/actualites/";
  private static final String FILE_END_PATH = "/image.";
  private static final String COMMA = ",";
  private static final String SEMICOLON = ";";
  private static final String FORMAT_PREFIX = "image/";

  private ImageConverterService imageConverterService;

  private FileServiceImpl(ImageConverterService imageConverterService) {
    this.imageConverterService = imageConverterService;
  }

  public static FileServiceImpl fromService(ImageConverterService imageConverterService) {
    return new FileServiceImpl(imageConverterService);
  }

  @Override
  public File saveFileOnSystem(String entityId, String base64FileContent) throws BaseException {

    createFoldersIfRequired(entityId);
    String format = extractFormatFromBase64(base64FileContent);
    Path path = computePath(entityId, format);
    byte[] convertedImageBytes = convertBase64ContentToBytes(entityId, base64FileContent);
    BufferedImage bufferedImage = readBytesToBufferedImage(entityId, convertedImageBytes);
    File fileTosave = instantiateFileFromPath(path);
    deleteFileIfExistsAndReturnResult(fileTosave);
    createNewFile(entityId, fileTosave);
    return writeBufferedImageToFile(bufferedImage, fileTosave, format);
  }

  String extractFormatFromBase64(String base64FileContent) {
    String formatOverhead = base64FileContent.split(COMMA)[0];
    String formatImage = formatOverhead.split(SEMICOLON)[0];
    return formatImage.split(FORMAT_PREFIX)[1];
  }

  void createFoldersIfRequired(String entityId) {
    createMainFolderIfRequired();
    createSubFolderIfRequired(entityId);
  }

  boolean createSubFolderIfRequired(String entityId) {
    boolean hasCreated = false;
    Path pathFolder = computeFolderPath(entityId);
    File fileFolder = new File(pathFolder.toUri());
    if (!fileFolder.exists()) {
      hasCreated = fileFolder.mkdir();
    }
    return hasCreated;
  }

  boolean createMainFolderIfRequired() {
    boolean hasCreated = false;
    Path mainFolderPath = computeMainFolderPath();
    File mainFileFolder = new File(mainFolderPath.toUri());
    if (!mainFileFolder.exists()) {
      hasCreated = mainFileFolder.mkdir();
    }
    return hasCreated;
  }

  Path computePath(String entityId, String format) {
    return Paths.get(FILE_BASE_PATH + entityId + FILE_END_PATH + format);
  }

  Path computeMainFolderPath() {
    return Paths.get(FILE_BASE_PATH);
  }

  Path computeFolderPath(String entityId) {
    return Paths.get(FILE_BASE_PATH + entityId);
  }

  File writeBufferedImageToFile(BufferedImage bufferedImage, File imageToSave, String format) throws BaseException {
    try {
      ImageIO.write(bufferedImage, format, imageToSave);
      return imageToSave;
    } catch (Exception e) {
      LOGGER.error("Impossible d'enregistrer l'image sur le système", e);
      throw new BaseException(e.getMessage());
    }
  }

  File instantiateFileFromPath(Path path) {
    return new File(path.toUri());
  }

  byte[] convertBase64ContentToBytes(String entityId, String base64FileContent) throws BaseException {
    try {
      return imageConverterService.getImageByteArray(base64FileContent);
    } catch (Exception e) {
      LOGGER.error("Impossible de convertir l'image avant son enregistrement pour l'entité d'id : " + entityId, e);
      throw new BaseException(e.getMessage());
    }
  }

  BufferedImage readBytesToBufferedImage(String entityId, byte[] convertedImageBytes) throws BaseException {
    try {
      return ImageIO.read(new ByteArrayInputStream(convertedImageBytes));
    } catch (Exception e) {
      LOGGER.error("Impossible de transformer l'image en buffered image pour l'entité : " + entityId, e);
      throw new BaseException(e.getMessage());
    }
  }

  void createNewFile(String entityId, File imageToSave) throws BaseException {
    try {
      imageToSave.createNewFile();
    } catch (IOException e) {
      LOGGER.error("Impossible de créer le fichier devant recevoir les données de l'image pour l'entité : " + entityId,
          e);
      throw new BaseException(e.getMessage());
    }
  }

  boolean deleteFileIfExistsAndReturnResult(File imageToSave) {
    boolean hasDeleted = false;
    if (imageToSave.exists()) {
      hasDeleted = imageToSave.delete();
    }
    return hasDeleted;
  }
}
