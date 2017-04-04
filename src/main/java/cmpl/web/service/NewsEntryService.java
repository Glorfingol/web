package cmpl.web.service;

import java.util.List;

import cmpl.web.model.news.dto.NewsEntryDTO;

public interface NewsEntryService extends BaseService<NewsEntryDTO> {

  List<NewsEntryDTO> getRecentNews();

}
