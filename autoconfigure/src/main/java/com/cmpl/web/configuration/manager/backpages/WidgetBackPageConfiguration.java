package com.cmpl.web.configuration.manager.backpages;

import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WidgetBackPageConfiguration {

  @Bean
  BackPage viewWidgets() {
    return BackPageBuilder.create().decorated(true).pageName("WIDGET_VIEW")
        .templatePath("back/widgets/view_widgets").titleKey("back.widgets.title").build();
  }

  @Bean
  BackPage createWidget() {
    return BackPageBuilder.create().decorated(true).pageName("WIDGET_CREATE")
        .templatePath("back/widgets/create_widget").titleKey("back.widgets.title").build();
  }

  @Bean
  BackPage updateWidget() {
    return BackPageBuilder.create().decorated(true).pageName("WIDGET_UPDATE")
        .templatePath("back/widgets/edit_widget").titleKey("back.widgets.title").build();
  }

}
