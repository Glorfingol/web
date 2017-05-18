package cmpl.web.service.impl;

import java.util.List;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;

import cmpl.web.model.BaseException;
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
  public List<Post> getRecentFeed() throws BaseException {

    if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
      throw new BaseException();
    }

    return facebookConnector.feedOperations().getFeed();
  }

}
