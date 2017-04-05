package cmpl.web.validator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cmpl.web.model.news.error.NEWS_ERROR;
import cmpl.web.model.news.error.NEWS_ERROR_CAUSE;
import cmpl.web.model.news.error.NewsEntryError;
import cmpl.web.model.news.error.NewsEntryErrorCause;
import cmpl.web.model.news.rest.news.NewsContentRequest;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsImageRequest;
import cmpl.web.validator.NewsEntryRequestValidator;

public class NewsEntryRequestValidatorImpl implements NewsEntryRequestValidator {

  private final MessageSource messageSource;

  private NewsEntryRequestValidatorImpl(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public static NewsEntryRequestValidatorImpl fromMessageSource(MessageSource messageSource) {
    return new NewsEntryRequestValidatorImpl(messageSource);
  }

  @Override
  public NewsEntryError validateCreate(NewsEntryRequest request, String languageCode) {

    List<NewsEntryErrorCause> causes = isContentValid(request.getContent(), languageCode);
    causes.addAll(isNewsEntryValid(request, languageCode));
    causes.addAll(isContentValid(request.getContent(), languageCode));
    causes.addAll(isNewsImageValid(request.getImage(), languageCode));

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public NewsEntryError validateUpdate(NewsEntryRequest request, String newsEntryId, String languageCode) {

    List<NewsEntryErrorCause> causes = isIdValid(newsEntryId, languageCode);
    causes.addAll(isNewsEntryValid(request, languageCode));
    causes.addAll(isContentValid(request.getContent(), languageCode));
    causes.addAll(isNewsImageValid(request.getImage(), languageCode));

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public NewsEntryError validateDelete(String newsEntryId, String languageCode) {
    List<NewsEntryErrorCause> causes = isIdValid(newsEntryId, languageCode);

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public NewsEntryError validateGet(String newsEntryId, String languageCode) {
    List<NewsEntryErrorCause> causes = isIdValid(newsEntryId, languageCode);

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }
    return null;
  }

  private List<NewsEntryErrorCause> isIdValid(String id, String languageCode) {
    List<NewsEntryErrorCause> causes = new ArrayList<NewsEntryErrorCause>();
    if (!isStringValid(id)) {

      Locale locale = new Locale(languageCode);

      NewsEntryErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_NEWS_ID, locale);
      causes.add(cause);
    }
    return causes;
  }

  private List<NewsEntryErrorCause> isContentValid(NewsContentRequest content, String languageCode) {
    List<NewsEntryErrorCause> causes = new ArrayList<NewsEntryErrorCause>();
    if (content == null) {
      return causes;
    }
    if (!isNewsContentValid(content)) {
      Locale locale = new Locale(languageCode);
      NewsEntryErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_CONTENT, locale);
      causes.add(cause);
    }
    return causes;
  }

  private List<NewsEntryErrorCause> isNewsEntryValid(NewsEntryRequest entry, String languageCode) {
    Locale locale = new Locale(languageCode);
    List<NewsEntryErrorCause> causes = new ArrayList<NewsEntryErrorCause>();

    if (!isStringValid(entry.getTitle())) {
      NewsEntryErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_TITLE, locale);
      causes.add(cause);

    }
    if (!isStringValid(entry.getAuthor())) {
      NewsEntryErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_AUTHOR, locale);
      causes.add(cause);
    }
    return causes;
  }

  private List<NewsEntryErrorCause> isNewsImageValid(NewsImageRequest imageRequest, String languageCode) {

    List<NewsEntryErrorCause> causes = new ArrayList<NewsEntryErrorCause>();
    if (imageRequest == null) {
      return causes;
    }
    Locale locale = new Locale(languageCode);

    if (!isStringValid(imageRequest.getAlt())) {
      NewsEntryErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_ALT, locale);
      causes.add(cause);

    }
    if (!isStringValid(imageRequest.getLegend())) {
      NewsEntryErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_LEGEND, locale);
      causes.add(cause);
    }
    if (!isIntValid(imageRequest.getHeight())) {
      NewsEntryErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_WIDTH, locale);
      causes.add(cause);
    }
    if (!isIntValid(imageRequest.getWidth())) {
      NewsEntryErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_HEIGHT, locale);
      causes.add(cause);
    }
    if (!isStringValid(imageRequest.getSrc())) {
      NewsEntryErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_SRC, locale);
      causes.add(cause);
    }

    return causes;
  }

  private NewsEntryError computeError(List<NewsEntryErrorCause> causes) {
    NewsEntryError error = new NewsEntryError();
    error.setCode(NEWS_ERROR.INVALID_REQUEST.toString());
    error.setCauses(causes);
    return error;
  }

  private NewsEntryErrorCause computeCause(NEWS_ERROR_CAUSE errorCause, Locale locale) {
    NewsEntryErrorCause cause = new NewsEntryErrorCause();
    cause.setCode(errorCause.toString());
    cause.setMessage(getI18n(errorCause.getCauseKey(), locale));
    return cause;
  }

  private boolean isIntValid(int value) {
    return value != -1;
  }

  private boolean isStringValid(String stringToValidate) {
    return !StringUtils.isEmpty(stringToValidate);
  }

  private boolean isNewsContentValid(NewsContentRequest content) {
    return isStringValid(content.getContent());
  }

  private String getI18n(String key, Locale locale) {
    return messageSource.getMessage(key, null, locale);
  }

}
