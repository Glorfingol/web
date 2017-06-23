package cmpl.web.dispatcher;

import java.util.Locale;

import cmpl.web.model.BaseException;
import cmpl.web.model.facebook.FacebookImportRequest;
import cmpl.web.model.facebook.FacebookImportResponse;

public interface FacebookDispatcher {

  FacebookImportResponse createEntity(FacebookImportRequest facebookImportRequest, Locale locale) throws BaseException;

}
