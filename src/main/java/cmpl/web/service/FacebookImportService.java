package cmpl.web.service;

import java.util.List;

import cmpl.web.model.facebook.FacebookImportPost;
import cmpl.web.model.news.dto.NewsEntryDTO;

public interface FacebookImportService {

  List<NewsEntryDTO> importFacebookPost(List<FacebookImportPost> facebookPosts);

}
