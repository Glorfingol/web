package cmpl.web.model.news.error;

import java.util.List;

public class NewsEntryError extends NewsEntryErrorCause {

  private List<NewsEntryErrorCause> causes;

  public List<NewsEntryErrorCause> getCauses() {
    return causes;
  }

  public void setCauses(List<NewsEntryErrorCause> causes) {
    this.causes = causes;
  }

}
