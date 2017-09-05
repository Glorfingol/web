package cmpl.web.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cmpl.web.core.model.Error;
import cmpl.web.core.model.ErrorCause;
import cmpl.web.message.WebMessageSourceImpl;

/**
 * Implementation de l'interface de validation des modifications de NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryRequestValidatorImpl implements NewsEntryRequestValidator {

  private final WebMessageSourceImpl messageSource;

  public NewsEntryRequestValidatorImpl(WebMessageSourceImpl messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public Error validateCreate(NewsEntryRequest request, Locale locale) {

    List<ErrorCause> causes = isNewsEntryValid(request, locale);
    causes.addAll(isContentValid(request.getContent(), locale));
    causes.addAll(isNewsImageValid(request.getImage(), locale));

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateUpdate(NewsEntryRequest request, String newsEntryId, Locale locale) {

    List<ErrorCause> causes = isIdValid(newsEntryId, locale);
    causes.addAll(isNewsEntryValid(request, locale));
    causes.addAll(isContentValid(request.getContent(), locale));
    causes.addAll(isNewsImageValid(request.getImage(), locale));

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateDelete(String newsEntryId, Locale locale) {
    List<ErrorCause> causes = isIdValid(newsEntryId, locale);

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateGet(String newsEntryId, Locale locale) {
    List<ErrorCause> causes = isIdValid(newsEntryId, locale);

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }
    return null;
  }

  List<ErrorCause> isIdValid(String id, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(id)) {

      ErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_NEWS_ID, locale);
      causes.add(cause);
    }
    return causes;
  }

  List<ErrorCause> isContentValid(NewsContentRequest content, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (content == null) {
      return causes;
    }
    if (!isNewsContentValid(content)) {
      ErrorCause cause = computeCause(NEWS_ERROR_CAUSE.EMPTY_CONTENT, locale);
      causes.add(cause);
    }
    return causes;
  }

  List<ErrorCause> isNewsEntryValid(NewsEntryRequest entry, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();

    if (entry == null) {
      return causes;
    }

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

  List<ErrorCause> isNewsImageValid(NewsImageRequest imageRequest, Locale locale) {

    List<ErrorCause> causes = new ArrayList<>();
    if (imageRequest == null) {
      return causes;
    }

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

    return causes;
  }

  Error computeError(List<ErrorCause> causes) {
    Error error = new Error();
    error.setCode(NEWS_ERROR.INVALID_REQUEST.toString());
    error.setCauses(causes);
    return error;
  }

  ErrorCause computeCause(NEWS_ERROR_CAUSE errorCause, Locale locale) {
    ErrorCause cause = new ErrorCause();
    cause.setCode(errorCause.toString());
    cause.setMessage(getI18n(errorCause.getCauseKey(), locale));
    return cause;
  }

  boolean isNewsContentValid(NewsContentRequest content) {
    return isStringValid(content.getContent());
  }

  boolean isStringValid(String stringToValidate) {
    return StringUtils.hasText(stringToValidate);
  }

  String getI18n(String key, Locale locale) {
    return messageSource.getMessage(key, null, locale);
  }

}
