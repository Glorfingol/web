package cmpl.web.service;

import java.io.File;

import cmpl.web.model.BaseException;

public interface FileService {

  File saveFileOnSystem(String entityId, String base64FileContent) throws BaseException;

}
