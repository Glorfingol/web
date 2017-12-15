package com.cmpl.web.style;

import java.util.Locale;

public interface StyleDispatcher {

  StyleResponse updateEntity(StyleForm form, Locale locale);

}
