package com.cmpl.web.core.file;

import com.cmpl.web.core.common.context.ContextHolder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * Implementation de l'enregistrement de fichiers dans le classpath
 *
 * @author Louis
 */
@CacheConfig(cacheNames = "files")
public class DefaultFileService implements FileService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFileService.class);

  private final ContextHolder contextHolder;

  public DefaultFileService(ContextHolder contextHolder) {

    this.contextHolder = Objects.requireNonNull(contextHolder);

  }

  @Override
  @CacheEvict(key = "#a0")
  public void saveFileOnSystem(String fileName, String content) {
    try {
      Files.write(Paths.get(contextHolder.getTemplateBasePath() + fileName),
        content == null ? "".getBytes() : content.getBytes());
    } catch (IOException e) {
      LOGGER.error("Impossible d'enregistrer le fichier " + fileName, e);
    }
  }

  @Override
  public String readFileContentFromSystem(String fileName) {
    return new String(readFileBytes(fileName, contextHolder.getTemplateBasePath()));
  }

  @Override
  @CacheEvict(key = "#a0")
  public void saveMediaOnSystem(String fileName, byte[] content) {
    try {
      Files.write(Paths.get(contextHolder.getMediaBasePath() + fileName), content);
    } catch (IOException e) {
      LOGGER.error("Impossible d'enregistrer le fichier " + fileName, e);
    }
  }

  @Override
  public InputStream read(String fileName) {
    return new ByteArrayInputStream(readFileBytes(fileName, contextHolder.getMediaBasePath()));
  }

  @Override
  @CacheEvict(key = "#a0")
  public void removeFileFromSystem(String fileName) {
    try {
      Files.delete(Paths.get(contextHolder.getTemplateBasePath() + fileName));
    } catch (IOException e) {
      LOGGER.error("Impossible de supprimer le fichier " + fileName, e);
    }
  }

  @Override
  @CacheEvict(key = "#a0")
  public void removeMediaFromSystem(String fileName) {
    try {
      Files.delete(Paths.get(contextHolder.getMediaBasePath() + fileName));
    } catch (IOException e) {
      LOGGER.error("Impossible de supprimer le fichier " + fileName, e);
    }
  }

  @Cacheable(key = "#a0", sync = true)
  public byte[] readFileBytes(String fileName, String basePath) {
    try {
      return Files.readAllBytes(Paths.get(basePath + fileName));
    } catch (IOException e) {
      return new byte[]{};
    }
  }

}
