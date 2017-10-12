package cmpl.web.media;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.core.service.BaseServiceImpl;
import cmpl.web.file.FileService;

public class MediaServiceImpl extends BaseServiceImpl<MediaDTO, Media> implements MediaService {

  private final ContextHolder contextHolder;
  private final FileService fileService;

  public MediaServiceImpl(MediaRepository entityRepository, FileService fileService, ContextHolder contextHolder) {
    super(entityRepository);
    this.fileService = fileService;
    this.contextHolder = contextHolder;
  }

  @Override
  @Transactional
  public MediaDTO upload(MultipartFile multipartFile) throws SQLException, IOException {

    String fileName = multipartFile.getOriginalFilename();
    String extension = fileName.split("\\.")[1];
    if (extension != null) {
      extension = extension.toLowerCase();
    }

    MediaDTO mediaToCreate = new MediaDTO();
    mediaToCreate.setName(fileName);
    mediaToCreate.setContentType(multipartFile.getContentType());
    mediaToCreate.setExtension(extension);
    mediaToCreate.setSize(multipartFile.getSize());
    mediaToCreate.setSrc(contextHolder.getMediaDisplayPath() + fileName);

    fileService.saveMediaOnSystem(fileName, multipartFile.getBytes());

    return createEntity(mediaToCreate);
  }

  @Override
  public InputStream download(long mediaId) throws SQLException {
    return null;
  }

  @Override
  protected MediaDTO toDTO(Media entity) {
    MediaDTO dto = new MediaDTO();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected Media toEntity(MediaDTO dto) {
    Media entity = new Media();
    fillObject(dto, entity);
    return entity;
  }

}
