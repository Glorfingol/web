package cmpl.web.factory;

import java.util.Locale;

public interface BaseFactory {

  String getI18nValue(String key, Locale locale);

}
