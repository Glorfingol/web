package cmpl.web.media;

import java.io.InputStream;
import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

import cmpl.web.core.service.BaseService;

public interface MediaService extends BaseService<MediaDTO> {

  MediaDTO upload(MultipartFile multipartFile) throws Exception;

  InputStream download(String mediaName) throws SQLException;

  MediaDTO findByName(String name);

}
