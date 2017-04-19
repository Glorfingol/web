package cmpl.web.validator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.news.error.Error;
import cmpl.web.model.news.error.ErrorCause;
import cmpl.web.model.news.error.NEWS_ERROR;
import cmpl.web.model.news.error.NEWS_ERROR_CAUSE;
import cmpl.web.model.news.rest.news.NewsContentRequest;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsImageRequest;
import cmpl.web.validator.NewsEntryRequestValidator;

public class NewsEntryRequestValidatorImpl implements NewsEntryRequestValidator {

  private final WebMessageSourceImpl messageSource;

  private static final String FORMAT_PNG = "png";

  private NewsEntryRequestValidatorImpl(WebMessageSourceImpl messageSource) {
    this.messageSource = messageSource;
  }

  public static NewsEntryRequestValidatorImpl fromMessageSource(WebMessageSourceImpl messageSource) {
    return new NewsEntryRequestValidatorImpl(messageSource);
  }

  @Override
  public Error validateCreate(NewsEntryRequest request, String languageCode) {

    List<ErrorCause> causes = isContentValid(request.getContent(), languageCode);
    causes.addAll(isNewsEntryValid(request, languageCode));
    causes.addAll(isNewsImageValid(request.getImage(), languageCode));

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateUpdate(NewsEntryRequest request, String newsEntryId, String languageCode) {

    List<ErrorCause> causes = isIdValid(newsEntryId, languageCode);
    causes.addAll(isNewsEntryValid(request, languageCode));
    causes.addAll(isContentValid(request.getContent(), languageCode));
    causes.addAll(isNewsImageValid(request.getImage(), languageCode));

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateDelete(String newsEntryId, String languageCode) {
    List<ErrorCause> causes = isIdValid(newsEntryId, languageCode);

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateGet(String newsEntryId, String languageCode) {
    List<ErrorCause> causes = isIdValid(newsEntryId, languageCode);

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }
    return null;
  }

  private List<ErrorCause> isIdValid(String id, String languageCode) {
    List<ErrorCause> causes = new ArrayList<ErrorCause>();
    if (!isStringValid(id)) {

      Locale locale = new Locale(languageCode);

      ErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_NEWS_ID, locale);
      causes.add(cause);
    }
    return causes;
  }

  private List<ErrorCause> isContentValid(NewsContentRequest content, String languageCode) {
    List<ErrorCause> causes = new ArrayList<ErrorCause>();
    if (content == null) {
      return causes;
    }
    if (!isNewsContentValid(content)) {
      Locale locale = new Locale(languageCode);
      ErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_CONTENT, locale);
      causes.add(cause);
    }
    return causes;
  }

  private List<ErrorCause> isNewsEntryValid(NewsEntryRequest entry, String languageCode) {
    Locale locale = new Locale(languageCode);
    List<ErrorCause> causes = new ArrayList<ErrorCause>();

    if (!isStringValid(entry.getTitle())) {
      ErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_TITLE, locale);
      causes.add(cause);

    }
    if (!isStringValid(entry.getAuthor())) {
      ErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_AUTHOR, locale);
      causes.add(cause);
    }
    return causes;
  }

  private List<ErrorCause> isNewsImageValid(NewsImageRequest imageRequest, String languageCode) {

    List<ErrorCause> causes = new ArrayList<ErrorCause>();
    if (imageRequest == null) {
      return causes;
    }
    Locale locale = new Locale(languageCode);

    if (!isStringValid(imageRequest.getAlt())) {
      ErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_ALT, locale);
      causes.add(cause);

    }
    if (!isStringValid(imageRequest.getLegend())) {
      ErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_LEGEND, locale);
      causes.add(cause);
    }
    if (!isStringValid(imageRequest.getSrc())) {
      ErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_SRC, locale);
      causes.add(cause);
    }
    if (!isImageFormatValid(imageRequest.getSrc())) {
      ErrorCause cause = computeCause(NEWS_ERROR_CAUSE.INVALID_FORMAT, locale);
      causes.add(cause);
    }

    return causes;
  }

  private Error computeError(List<ErrorCause> causes) {
    Error error = new Error();
    error.setCode(NEWS_ERROR.INVALID_REQUEST.toString());
    error.setCauses(causes);
    return error;
  }

  private ErrorCause computeCause(NEWS_ERROR_CAUSE errorCause, Locale locale) {
    ErrorCause cause = new ErrorCause();
    cause.setCode(errorCause.toString());
    cause.setMessage(getI18n(errorCause.getCauseKey(), locale));
    return cause;
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

  private boolean isImageFormatValid(String src) {
    return src.contains(FORMAT_PNG);
  }

}
