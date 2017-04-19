package cmpl.web.message;

import java.util.Locale;

public interface WebMessageSource {

  String getI18n(String code, Locale locale);

}
