package cmpl.web.service;

import java.util.List;

import org.springframework.social.facebook.api.Post;

import cmpl.web.model.BaseException;

public interface FacebookService {

  List<Post> getRecentFeed() throws BaseException;

}
