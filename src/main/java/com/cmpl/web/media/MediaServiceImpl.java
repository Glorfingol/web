package com.cmpl.web.media;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.service.BaseServiceImpl;
import com.cmpl.web.file.FileService;

@CacheConfig(cacheNames = {"modelPage"})
public class MediaServiceImpl extends BaseServiceImpl<MediaDTO, Media> implements MediaService {

  private final ContextHolder contextHolder;
  private final FileService fileService;
  private final MediaRepository mediaRepository;

  public MediaServiceImpl(MediaRepository entityRepository, FileService fileService, ContextHolder contextHolder) {
    super(entityRepository);
    this.fileService = fileService;
    this.contextHolder = contextHolder;
    this.mediaRepository = entityRepository;
  }

  @Override
  @Transactional
  public MediaDTO upload(MultipartFile multipartFile) throws SQLException, IOException {

    String fileName = multipartFile.getOriginalFilename();
    String extension = fileName.split("\\.")[1];
    if (extension != null) {
      extension = extension.toLowerCase();
    }

    MediaDTO mediaToCreate = MediaDTOBuilder.create().name(fileName).contentType(multipartFile.getContentType())
        .extension(extension).size(multipartFile.getSize()).src(contextHolder.getMediaDisplayPath() + fileName).build();

    fileService.saveMediaOnSystem(fileName, multipartFile.getBytes());

    return createEntity(mediaToCreate);
  }

  @Override
  public InputStream download(String mediaName) throws SQLException {
    return fileService.read(mediaName);
  }

  @Override
  protected MediaDTO toDTO(Media entity) {
    MediaDTO dto = MediaDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected Media toEntity(MediaDTO dto) {
    Media entity = MediaBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }

  @Override
  public MediaDTO findByName(String name) {
    return toDTO(mediaRepository.findByName(name));
  }

}
