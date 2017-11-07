package cmpl.web.style;

import cmpl.web.core.service.BaseServiceImpl;
import cmpl.web.file.FileService;
import cmpl.web.media.MediaDTO;
import cmpl.web.media.MediaService;

public class StyleServiceImpl extends BaseServiceImpl<StyleDTO, Style> implements StyleService {

  private final StyleRepository styleRepository;
  private final MediaService mediaService;
  private final FileService fileService;

  public StyleServiceImpl(StyleRepository styleRepository, MediaService mediaService, FileService fileService) {
    super(styleRepository);
    this.styleRepository = styleRepository;
    this.mediaService = mediaService;
    this.fileService = fileService;
  }

  @Override
  public StyleDTO getStyle() {
    return toDTO(styleRepository.findAll().get(0));
  }

  @Override
  public StyleDTO updateEntity(StyleDTO dto) {

    String content = dto.getContent();
    fileService.saveMediaOnSystem(dto.getMedia().getName(), content.getBytes());

    return toDTO(styleRepository.save(toEntity(dto)));

  }

  @Override
  public StyleDTO createEntity(StyleDTO dto) {

    String content = dto.getContent();
    fileService.saveMediaOnSystem(dto.getMedia().getName(), content.getBytes());

    return toDTO(styleRepository.save(toEntity(dto)));

  }

  @Override
  protected StyleDTO toDTO(Style entity) {
    StyleDTO dto = new StyleDTO();

    fillObject(entity, dto);
    MediaDTO media = mediaService.getEntity(Long.valueOf(entity.getMediaId()));
    dto.setMedia(media);

    String content = fileService.readFileContentFromSystem(media.getName());
    dto.setContent(content);

    return dto;
  }

  @Override
  protected Style toEntity(StyleDTO dto) {
    Style entity = new Style();

    fillObject(dto, entity);
    entity.setMediaId(String.valueOf(dto.getMedia().getId()));
    return entity;
  }

}
