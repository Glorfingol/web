package cmpl.web.translator;

import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsEntryResponse;

public interface NewsEntryTranslator {

  NewsEntryDTO fromRequestToDTO(NewsEntryRequest request);

  NewsEntryResponse fromDTOToResponse(NewsEntryDTO dto);
}
