package com.cmpl.web.news;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryDisplayBeanTest {

  private String labelPar;

  private String labelLe;

  private DateTimeFormatter dateFormat;

  private String labelAccroche;

  private String imageDisplaySrc;

  private String showHref;

  @Before
  public void setUp() {
    labelPar = "Par";
    labelLe = "le";
    labelAccroche = "En savoir plus";
    dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");
    imageDisplaySrc = "http://cm-pl.com/";
    showHref = "/pages/actualites/666";
  }

  @Test
  public void testGetTags_Empty() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    List<String> result = displayBean.getTags();
    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testGetTags_Many() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().tags("tag;another").build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    List<String> result = displayBean.getTags();
    Assert.assertTrue(result.size() == 2);
    Assert.assertEquals("tag", result.get(0));
    Assert.assertEquals("another", result.get(1));
  }

  @Test
  public void testGetTags_One() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().tags("unseul").build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    List<String> result = displayBean.getTags();
    Assert.assertTrue(result.size() == 1);
    Assert.assertEquals("unseul", result.get(0));
  }

  @Test
  public void testGetNewsEntryShowMore() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getNewsEntryShowMore();
    Assert.assertEquals(labelAccroche, result);
  }

  @Test
  public void testGetNewsEntryShowHref() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().id(Long.valueOf("666")).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getNewsEntryShowHref();
    Assert.assertEquals("/pages/actualites/666", result);
  }

  @Test
  public void testGetNewsEntryReadHref() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().id(Long.valueOf("666")).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getNewsEntryReadHref();
    Assert.assertEquals("/news/666", result);
  }

  @Test
  public void testgetNewsEntryId() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().id(Long.valueOf("666")).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getNewsEntryId();
    Assert.assertEquals("666", result);
  }

  @Test
  public void testIsDisplayTags_True() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().tags("tag").build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayTags();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsDisplayTags_False() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayTags();
    Assert.assertFalse(result);
  }

  @Test
  public void testIsDisplayContent_True() {
    NewsContentDTO content = new NewsContentDTOBuilder().build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayContent();
    Assert.assertTrue(result);

    result = displayBean.displayContent();
    Assert.assertTrue(result);

  }

  @Test
  public void testIsDisplayContent_False() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayContent();
    Assert.assertFalse(result);

  }

  @Test
  public void testIsDisplayContent_False_Link() {
    NewsContentDTO content = new NewsContentDTOBuilder().linkUrl("someLink").build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayContent();
    Assert.assertFalse(result);

  }

  @Test
  public void testIsDisplayImage_True() {
    NewsImageDTO image = new NewsImageDTOBuilder().build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayImage();
    Assert.assertTrue(result);

    result = displayBean.displayImage();
    Assert.assertTrue(result);

  }

  @Test
  public void testIsDisplayImage_False() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayImage();
    Assert.assertFalse(result);

    result = displayBean.displayImage();
    Assert.assertFalse(result);
  }

  @Test
  public void testGetImageHeight_No_Image() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    int height = displayBean.getImageHeight();
    Assert.assertEquals(0, height);

  }

  @Test
  public void testGetImageHeight_With_Image() {
    NewsImageDTO image = new NewsImageDTOBuilder().height(500).build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    int height = displayBean.getImageHeight();
    Assert.assertEquals(500, height);
  }

  @Test
  public void testGetImageWidth_No_Image() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    int result = displayBean.getImageWidth();
    Assert.assertEquals(0, result);

  }

  @Test
  public void testGetImageWidth_With_Image() {
    NewsImageDTO image = new NewsImageDTOBuilder().width(500).build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    int result = displayBean.getImageWidth();
    Assert.assertEquals(500, result);
  }

  @Test
  public void testGetAlt_No_Image() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getAlt();
    Assert.assertEquals("", result);

  }

  @Test
  public void testGetAlt_With_Image() {
    NewsImageDTO image = new NewsImageDTOBuilder().alt("alt").build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getAlt();
    Assert.assertEquals("alt", result);
  }

  @Test
  public void testGetLegend_No_Image() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getLegend();
    Assert.assertEquals("", result);

  }

  @Test
  public void testGetLegend_With_Image() {
    NewsImageDTO image = new NewsImageDTOBuilder().legend("legend").build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getLegend();
    Assert.assertEquals("legend", result);
  }

  @Test
  public void testGetSrcNo_Image() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getImage();
    Assert.assertEquals("", result);

  }

  @Test
  public void testGetSrc_With_Image() {
    NewsImageDTO image = new NewsImageDTOBuilder().src("src").build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getImage();
    Assert.assertEquals(imageDisplaySrc + "src", result);
  }

  @Test
  public void testGetContent_No_Content() {

    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getContent();
    Assert.assertEquals("", result);

  }

  @Test
  public void testGetContent_With_Content() {
    NewsContentDTO content = new NewsContentDTOBuilder().content("content").build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getContent();
    Assert.assertEquals("content", result);
  }

  @Test
  public void testGetTitle() {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().title("title").build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getTitle();
    Assert.assertEquals("title", result);
  }

  @Test
  public void testGetPublicationDate() {

    LocalDate publicationDate = LocalDate.of(2017, 9, 10);
    NewsEntryDTO entry = new NewsEntryDTOBuilder().creationDate(publicationDate).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getPublicationDate();
    Assert.assertEquals("10/09/17", result);

  }

  @Test
  public void testGetPanelHeading() {

    String SPACE = " ";
    LocalDate publicationDate = LocalDate.of(2017, 9, 10);
    NewsEntryDTO entry = new NewsEntryDTOBuilder().author("Test").creationDate(publicationDate).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    StringBuilder panelHeadingBuilder = new StringBuilder();
    panelHeadingBuilder.append(labelPar).append(SPACE).append("Test").append(SPACE).append(labelLe).append(SPACE)
        .append("10/09/17");

    String result = displayBean.getPanelHeading();
    Assert.assertEquals(panelHeadingBuilder.toString(), result);

  }

  @Test
  public void testGetLinkUrl() throws Exception {
    NewsContentDTO content = new NewsContentDTOBuilder().linkUrl("link").build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getLinkUrl();
    Assert.assertEquals("link", result);
  }

  @Test
  public void testGetVideoUrl() throws Exception {
    NewsContentDTO content = new NewsContentDTOBuilder().videoUrl("video").build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getVideoUrl();
    Assert.assertEquals("video", result);
  }

  @Test
  public void testIsDisplayLink_True() throws Exception {
    NewsContentDTO content = new NewsContentDTOBuilder().linkUrl("link").build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayLink();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsDisplayLink_False_No_Content() throws Exception {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayLink();
    Assert.assertFalse(result);
  }

  @Test
  public void testIsDisplayLink_False_No_Link() throws Exception {
    NewsContentDTO content = new NewsContentDTOBuilder().build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayLink();
    Assert.assertFalse(result);
  }

  @Test
  public void testIsDisplayVideo() throws Exception {
    NewsContentDTO content = new NewsContentDTOBuilder().videoUrl("video").build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayVideo();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsDisplayVideo_False_No_Content() throws Exception {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayVideo();
    Assert.assertFalse(result);
  }

  @Test
  public void testIsDisplayVideo_False_No_Video() throws Exception {
    NewsContentDTO content = new NewsContentDTOBuilder().build();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    boolean result = displayBean.isDisplayVideo();
    Assert.assertFalse(result);
  }

  @Test
  public void testGetNewsEntryModifyHref() throws Exception {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().id(123456789L).build();
    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat,
        labelAccroche, showHref);

    String result = displayBean.getNewsEntryModifyHref();

    Assert.assertEquals("/manager/news/123456789", result);
  }

}
