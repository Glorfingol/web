package cmpl.web.service;

import cmpl.web.model.news.dto.NewsEntryDTO;

public interface NewsEntryService extends BaseService<NewsEntryDTO> {

  boolean isAlreadyImportedFromFacebook(String facebookId);

}
