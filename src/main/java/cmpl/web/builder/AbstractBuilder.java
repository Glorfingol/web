package cmpl.web.builder;

import java.util.Locale;

public abstract class AbstractBuilder {

  protected abstract String getI18nValue(String key, Locale locale);
}
