package cmpl.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Post.PostType;
import org.springframework.util.StringUtils;

import cmpl.web.model.BaseException;
import cmpl.web.model.facebook.ImportablePost;
import cmpl.web.service.FacebookService;
import cmpl.web.service.NewsEntryService;

/**
 * Implementation de l'interface qui va recuperer les posts facebook d'unutilisateur
 * 
 * @author Louis
 *
 */
public class FacebookServiceImpl implements FacebookService {

  private final Facebook facebookConnector;
  private final ConnectionRepository connectionRepository;
  private final String dateFormat;
  private final NewsEntryService newsEntryService;

  private FacebookServiceImpl(Facebook facebookConnector, ConnectionRepository connectionRepository,
      NewsEntryService newsEntryService, String dateFormat) {
    this.facebookConnector = facebookConnector;
    this.connectionRepository = connectionRepository;
    this.dateFormat = dateFormat;
    this.newsEntryService = newsEntryService;
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param facebookConnector
   * @param connectionRepository
   * @param newsEntryService
   * @param dateFormat
   * @return
   */
  public static FacebookServiceImpl fromFacebookConnector(Facebook facebookConnector,
      ConnectionRepository connectionRepository, NewsEntryService newsEntryService, String dateFormat) {
    return new FacebookServiceImpl(facebookConnector, connectionRepository, newsEntryService, dateFormat);
  }

  @Override
  public List<ImportablePost> getRecentFeed() throws BaseException {

    if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
      throw new BaseException();
    }

    PagedList<Post> recentPosts = facebookConnector.feedOperations().getPosts();

    return computeImportablePosts(recentPosts);
  }

  List<ImportablePost> computeImportablePosts(PagedList<Post> recentPosts) {
    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
    List<ImportablePost> importablePosts = new ArrayList<>();
    for (Post recentPost : recentPosts) {
      ImportablePost post = computeImportablePost(recentPost, formatter);
      if (canImportPost(post)) {
        importablePosts.add(post);
      }
    }
    return importablePosts;
  }

  private boolean canImportPost(ImportablePost post) {
    if (PostType.STATUS.equals(post.getType()) && StringUtils.isEmpty(post.getDescription())) {
      return false;
    }
    if (newsEntryService.isAlreadyImportedFromFacebook(post.getFacebookId())) {
      return false;
    }
    return true;
  }

  private ImportablePost computeImportablePost(Post feed, SimpleDateFormat formatter) {
    ImportablePost post = new ImportablePost();
    post.setAuthor(feed.getFrom().getName());
    post.setDescription(computeDescription(feed));
    post.setPhotoUrl(computePhotoUrl(feed));
    post.setLinkUrl(feed.getLink());
    post.setVideoUrl(computeVideoUrl(feed));
    post.setTitle(computeTitle(feed));
    post.setType(feed.getType());
    post.setFacebookId(feed.getId());
    post.setOnclick(computeOnclick(feed));
    post.setCreationDate(feed.getCreatedTime());
    post.setObjectId(feed.getObjectId());

    post.setFormattedDate(formatter.format(feed.getCreatedTime()));

    return post;
  }

  private String computePhotoUrl(Post feed) {
    if (!PostType.PHOTO.equals(feed.getType())) {
      return "";
    }
    return feed.getPicture();
  }

  private String computeVideoUrl(Post feed) {
    String videoUrl = feed.getSource();
    if (StringUtils.isEmpty(videoUrl)) {
      return videoUrl;
    }
    videoUrl = videoUrl.replace("autoplay=1", "autoplay=0");
    return videoUrl;
  }

  private String computeOnclick(Post feed) {
    return "toggleImport('" + feed.getId() + "')";
  }

  private String computeTitle(Post feed) {
    String title = feed.getName();
    if (StringUtils.isEmpty(title)) {
      title = feed.getCaption();
    }
    if (StringUtils.isEmpty(title)) {
      title = "Type " + feed.getType().name();
    }
    return title;
  }

  private String computeDescription(Post feed) {
    String message = feed.getMessage();
    if (StringUtils.isEmpty(message)) {
      return feed.getDescription();
    }
    return message;
  }

}
