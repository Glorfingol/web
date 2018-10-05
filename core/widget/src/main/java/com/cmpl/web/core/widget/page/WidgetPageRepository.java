package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.WidgetPage;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetPageRepository extends BaseRepository<WidgetPage> {

  List<WidgetPage> findByPageId(Long pageId);

  List<WidgetPage> findByWidgetId(Long widgetId);

  WidgetPage findByPageIdAndWidgetId(Long pageId, Long widgetId);

}
