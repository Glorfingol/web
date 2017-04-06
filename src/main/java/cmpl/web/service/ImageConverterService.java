package cmpl.web.service;

import cmpl.web.model.news.dto.NewsImageDTO;

public interface ImageConverterService {

  NewsImageDTO computeNewsImageFromString(String base64);

}
