package cmpl.web.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation de l'enregistrement de fichiers dans le classpath
 * 
 * @author Louis
 *
 */
public class FileServiceImpl implements FileService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

  private FileServiceImpl() {

  }

  /**
   * Constructeur static pour configuration
   * 
   * @return
   */
  public static FileServiceImpl voidConstructor() {
    return new FileServiceImpl();
  }

  @Override
  public void saveFileOnSystem(String fileName, String content) {
    try {
      Files.write(Paths.get(fileName), content.getBytes());
    } catch (IOException e) {
      LOGGER.error("Impossible d'enregistrer le fichier " + fileName, e);
    }
  }

  @Override
  public String readFileContentFromSystem(String fileName) {
    try {
      return new String(Files.readAllBytes(Paths.get(fileName)));
    } catch (IOException e) {
      LOGGER.error("Impossible de lire le contenu du fichier " + fileName, e);
    }
    return null;
  }
}
