package cmpl.web.service;

import java.io.IOException;

import cmpl.web.model.news.dto.NewsImageDTO;

public interface ImageConverterService {

  NewsImageDTO computeNewsImageFromString(String base64);

  byte[] getImageByteArray(String base64) throws IOException;

}
