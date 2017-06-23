package cmpl.web.translator;

import java.util.List;

import cmpl.web.model.facebook.FacebookImportPost;
import cmpl.web.model.facebook.FacebookImportRequest;
import cmpl.web.model.facebook.FacebookImportResponse;
import cmpl.web.model.news.dto.NewsEntryDTO;

public interface FacebookImportTranslator {

  List<FacebookImportPost> fromRequestToPosts(FacebookImportRequest request);

  FacebookImportResponse fromDTOToResponse(List<NewsEntryDTO> dtos);

}
