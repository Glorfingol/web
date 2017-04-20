package cmpl.web.factory;

import java.util.Locale;

public interface BaseDisplayFactory extends BaseFactory {

  String computeMainTitle(Locale locale);

  String computeTileName(String tileName, Locale locale);

  String computeHiddenLink(Locale locale);
}
