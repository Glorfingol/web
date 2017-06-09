package cmpl.web.service.impl;

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

public class FacebookServiceImpl implements FacebookService {

  private final Facebook facebookConnector;
  private final ConnectionRepository connectionRepository;

  private FacebookServiceImpl(Facebook facebookConnector, ConnectionRepository connectionRepository) {
    this.facebookConnector = facebookConnector;
    this.connectionRepository = connectionRepository;
  }

  public static FacebookServiceImpl fromFacebookConnector(Facebook facebookConnector,
      ConnectionRepository connectionRepository) {
    return new FacebookServiceImpl(facebookConnector, connectionRepository);
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
    List<ImportablePost> importablePosts = new ArrayList<>();
    for (Post recentPost : recentPosts) {
      ImportablePost post = computeImportablePost(recentPost);
      importablePosts.add(post);
    }
    return importablePosts;
  }

  private ImportablePost computeImportablePost(Post feed) {
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
    return post;
  }

  private String computePhotoUrl(Post feed) {
    if (!PostType.PHOTO.equals(feed.getType())) {
      return "";
    }
    // Pour enregistrer image
    // byte[] test = facebookConnector.mediaOperations().getAlbumImage(feed.getObjectId(), ImageType.NORMAL);
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
