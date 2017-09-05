package cmpl.web.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cmpl.web.core.context.ContextHolder;

/**
 * Implementation de l'enregistrement de fichiers dans le classpath
 * 
 * @author Louis
 *
 */
public class FileServiceImpl implements FileService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

  private final ContextHolder contextHolder;

  public FileServiceImpl(ContextHolder contextHolder) {
    this.contextHolder = contextHolder;
  }

  @Override
  public void saveFileOnSystem(String fileName, String content) {
    try {
      Files.write(Paths.get(contextHolder.getTemplateBasePath() + fileName), content.getBytes());
    } catch (IOException e) {
      LOGGER.error("Impossible d'enregistrer le fichier " + fileName, e);
    }
  }

  @Override
  public String readFileContentFromSystem(String fileName) {
    try {
      return new String(Files.readAllBytes(Paths.get(contextHolder.getTemplateBasePath() + fileName)));
    } catch (IOException e) {
      LOGGER.error("Impossible de lire le contenu du fichier " + fileName, e);
    }
    return null;
  }
}
