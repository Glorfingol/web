package cmpl.web.service;

import java.util.Locale;

import cmpl.web.model.BaseException;

public interface SitemapService {

  String createSiteMap(Locale locale) throws BaseException;

}
