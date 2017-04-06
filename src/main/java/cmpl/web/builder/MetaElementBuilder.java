package cmpl.web.builder;

import java.util.List;
import java.util.Locale;

import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.page.PAGE;

public interface MetaElementBuilder {

  List<MetaElement> computeMetaElementsForPage(Locale locale, PAGE page);

  List<MetaElement> computeMetaElementsForBackPage(Locale locale);

}
