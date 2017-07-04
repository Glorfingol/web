package cmpl.web.model.news.display;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

import cmpl.web.builder.NewsContentDTOBuilder;
import cmpl.web.builder.NewsEntryDTOBuilder;
import cmpl.web.builder.NewsImageDTOBuilder;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.dto.NewsImageDTO;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryDisplayBeanTest {

  private String labelPar;

  private String labelLe;

  private String dateFormat;

  private String labelAccroche;

  @Before
  public void setUp() {
    labelPar = "Par";
    labelLe = "le";
    labelAccroche = "En savoir plus";
    dateFormat = "dd/MM/yy";
  }

  @Test
  public void testGetTags_Empty() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    List<String> result = displayBean.getTags();
    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testGetTags_Many() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().tags("tag;another").toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    List<String> result = displayBean.getTags();
    Assert.assertTrue(result.size() == 2);
    Assert.assertEquals("tag", result.get(0));
    Assert.assertEquals("another", result.get(1));
  }

  @Test
  public void testGetTags_One() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().tags("unseul").toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    List<String> result = displayBean.getTags();
    Assert.assertTrue(result.size() == 1);
    Assert.assertEquals("unseul", result.get(0));
  }

  @Test
  public void testGetNewsEntryShowMore() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getNewsEntryShowMore();
    Assert.assertEquals(labelAccroche, result);
  }

  @Test
  public void testGetNewsEntryShowHref() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().id(Long.valueOf("666")).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getNewsEntryShowHref();
    Assert.assertEquals("/actualites/666", result);
  }

  @Test
  public void testGetNewsEntryReadHref() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().id(Long.valueOf("666")).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getNewsEntryReadHref();
    Assert.assertEquals("/news/666", result);
  }

  @Test
  public void testgetNewsEntryId() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().id(Long.valueOf("666")).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getNewsEntryId();
    Assert.assertEquals("666", result);
  }

  @Test
  public void testIsDisplayTags_True() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().tags("tag").toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayTags();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsDisplayTags_False() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayTags();
    Assert.assertFalse(result);
  }

  @Test
  public void testIsDisplayContent_True() {
    NewsContentDTO content = new NewsContentDTOBuilder().toNewsContentDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayContent();
    Assert.assertTrue(result);

    result = displayBean.displayContent();
    Assert.assertTrue(result);

  }

  @Test
  public void testIsDisplayContent_False() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayContent();
    Assert.assertFalse(result);

  }

  @Test
  public void testIsDisplayContent_False_Link() {
    NewsContentDTO content = new NewsContentDTOBuilder().linkUrl("someLink").toNewsContentDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayContent();
    Assert.assertFalse(result);

  }

  @Test
  public void testIsDisplayImage_True() {
    NewsImageDTO image = new NewsImageDTOBuilder().toNewsImageDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayImage();
    Assert.assertTrue(result);

    result = displayBean.displayImage();
    Assert.assertTrue(result);

  }

  @Test
  public void testIsDisplayImage_False() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayImage();
    Assert.assertFalse(result);

    result = displayBean.displayImage();
    Assert.assertFalse(result);
  }

  @Test
  public void testGetImageHeight_No_Image() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    int height = displayBean.getImageHeight();
    Assert.assertEquals(0, height);

  }

  @Test
  public void testGetImageHeight_With_Image() {
    NewsImageDTO image = new NewsImageDTOBuilder().height(500).toNewsImageDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    int height = displayBean.getImageHeight();
    Assert.assertEquals(500, height);
  }

  @Test
  public void testGetImageWidth_No_Image() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    int result = displayBean.getImageWidth();
    Assert.assertEquals(0, result);

  }

  @Test
  public void testGetImageWidth_With_Image() {
    NewsImageDTO image = new NewsImageDTOBuilder().width(500).toNewsImageDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    int result = displayBean.getImageWidth();
    Assert.assertEquals(500, result);
  }

  @Test
  public void testGetAlt_No_Image() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getAlt();
    Assert.assertEquals("", result);

  }

  @Test
  public void testGetAlt_With_Image() {
    NewsImageDTO image = new NewsImageDTOBuilder().alt("alt").toNewsImageDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getAlt();
    Assert.assertEquals("alt", result);
  }

  @Test
  public void testGetLegend_No_Image() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getLegend();
    Assert.assertEquals("", result);

  }

  @Test
  public void testGetLegend_With_Image() {
    NewsImageDTO image = new NewsImageDTOBuilder().legend("legend").toNewsImageDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getLegend();
    Assert.assertEquals("legend", result);
  }

  @Test
  public void testGetSrcNo_Image() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getImage();
    Assert.assertEquals("", result);

  }

  @Test
  public void testGetSrc_With_Image() {
    NewsImageDTO image = new NewsImageDTOBuilder().src("src").toNewsImageDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getImage();
    Assert.assertEquals("src", result);
  }

  @Test
  public void testGetContent_No_Content() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getContent();
    Assert.assertEquals("", result);

  }

  @Test
  public void testGetContent_With_Content() {
    NewsContentDTO content = new NewsContentDTOBuilder().content("content").toNewsContentDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getContent();
    Assert.assertEquals("content", result);
  }

  @Test
  public void testGetTitle() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().title("title").toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getTitle();
    Assert.assertEquals("title", result);
  }

  @Test
  public void testGetPublicationDate() {

    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 2017);
    calendar.set(Calendar.MONTH, 9);
    calendar.set(Calendar.DATE, 10);
    NewsEntryDTO entry = new NewsEntryDTOBuilder().creationDate(calendar.getTime()).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getPublicationDate();
    Assert.assertEquals("10/10/17", result);

  }

  @Test
  public void testGetPanelHeading() {

    String SPACE = " ";
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 2017);
    calendar.set(Calendar.MONTH, 9);
    calendar.set(Calendar.DATE, 10);
    NewsEntryDTO entry = new NewsEntryDTOBuilder().author("Test").creationDate(calendar.getTime()).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    StringBuilder panelHeadingBuilder = new StringBuilder();
    panelHeadingBuilder.append(labelPar).append(SPACE).append("Test").append(SPACE).append(labelLe).append(SPACE)
        .append("10/10/17");

    String result = displayBean.getPanelHeading();
    Assert.assertEquals(panelHeadingBuilder.toString(), result);

  }

  @Test
  public void testGetLinkUrl() throws Exception {
    NewsContentDTO content = new NewsContentDTOBuilder().linkUrl("link").toNewsContentDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getLinkUrl();
    Assert.assertEquals("link", result);
  }

  @Test
  public void testGetVideoUrl() throws Exception {
    NewsContentDTO content = new NewsContentDTOBuilder().videoUrl("video").toNewsContentDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getVideoUrl();
    Assert.assertEquals("video", result);
  }

  @Test
  public void testIsDisplayLink_True() throws Exception {
    NewsContentDTO content = new NewsContentDTOBuilder().linkUrl("link").toNewsContentDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayLink();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsDisplayLink_False_No_Content() throws Exception {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayLink();
    Assert.assertFalse(result);
  }

  @Test
  public void testIsDisplayLink_False_No_Link() throws Exception {
    NewsContentDTO content = new NewsContentDTOBuilder().toNewsContentDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayLink();
    Assert.assertFalse(result);
  }

  @Test
  public void testIsDisplayVideo() throws Exception {
    NewsContentDTO content = new NewsContentDTOBuilder().videoUrl("video").toNewsContentDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayVideo();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsDisplayVideo_False_No_Content() throws Exception {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayVideo();
    Assert.assertFalse(result);
  }

  @Test
  public void testIsDisplayVideo_False_No_Video() throws Exception {
    NewsContentDTO content = new NewsContentDTOBuilder().toNewsContentDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).toNewsEntryDTO();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    boolean result = displayBean.isDisplayVideo();
    Assert.assertFalse(result);
  }

  @Test
  public void testGetNewsEntryModifyHref() throws Exception {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().id(123456789L).toNewsEntryDTO();
    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, labelPar, labelLe, dateFormat, labelAccroche);

    String result = displayBean.getNewsEntryModifyHref();

    Assert.assertEquals("/manager/news/123456789", result);
  }

}
