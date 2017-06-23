package cmpl.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import cmpl.web.model.facebook.FacebookImportPost;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.service.FacebookImportService;
import cmpl.web.service.NewsEntryService;

public class FacebookImportServiceImpl implements FacebookImportService {

  private final NewsEntryService newsEntryService;

  private FacebookImportServiceImpl(NewsEntryService newsEntryService) {
    this.newsEntryService = newsEntryService;
  }

  public static FacebookImportServiceImpl fromService(NewsEntryService newsEntryService) {
    return new FacebookImportServiceImpl(newsEntryService);
  }

  @Override
  public List<NewsEntryDTO> importFacebookPost(List<FacebookImportPost> facebookPosts) {
    List<NewsEntryDTO> createdEntries = new ArrayList<>();
    for (FacebookImportPost postToImport : facebookPosts) {
      NewsEntryDTO createdPost = newsEntryService.createEntity(convertPostToNewsEntry(postToImport));
      createdEntries.add(createdPost);
    }
    return createdEntries;
  }

  NewsEntryDTO convertPostToNewsEntry(FacebookImportPost facebookPost) {
    NewsEntryDTO convertedPost = new NewsEntryDTO();

    return convertedPost;
  }

}
