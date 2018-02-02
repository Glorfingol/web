package com.cmpl.web.core.news;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryDisplayBeanTest {

  private String labelPar;

  private String labelLe;

  private DateTimeFormatter dateFormat;


  private String imageDisplaySrc;


  @Before
  public void setUp() {
    labelPar = "Par";
    labelLe = "le";
    dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");
    imageDisplaySrc = "http://cm-pl.com/";
  }

  @Test
  public void testGetTags_Empty() {

    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    List<String> result = displayBean.getTags();
    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testGetTags_Many() {

    NewsEntryDTO entry = NewsEntryDTOBuilder.create().tags("tag;another").build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat
        );

    List<String> result = displayBean.getTags();
    Assert.assertTrue(result.size() == 2);
    Assert.assertEquals("tag", result.get(0));
    Assert.assertEquals("another", result.get(1));
  }

  @Test
  public void testGetTags_One() {

    NewsEntryDTO entry = NewsEntryDTOBuilder.create().tags("unseul").build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    List<String> result = displayBean.getTags();
    Assert.assertTrue(result.size() == 1);
    Assert.assertEquals("unseul", result.get(0));
  }




  @Test
  public void testGetNewsEntryReadHref() {
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().id(Long.valueOf("666")).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat
        );

    String result = displayBean.getNewsEntryReadHref();
    Assert.assertEquals("/news/666", result);
  }

  @Test
  public void testgetNewsEntryId() {
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().id(Long.valueOf("666")).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat
        );

    String result = displayBean.getNewsEntryId();
    Assert.assertEquals("666", result);
  }

  @Test
  public void testIsDisplayTags_True() {
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().tags("tag").build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayTags();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsDisplayTags_False() {
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayTags();
    Assert.assertFalse(result);
  }

  @Test
  public void testIsDisplayContent_True() {
    NewsContentDTO content = NewsContentDTOBuilder.create().build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayContent();
    Assert.assertTrue(result);

    result = displayBean.displayContent();
    Assert.assertTrue(result);

  }

  @Test
  public void testIsDisplayContent_False() {
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayContent();
    Assert.assertFalse(result);

  }

  @Test
  public void testIsDisplayContent_False_Link() {
    NewsContentDTO content = NewsContentDTOBuilder.create().linkUrl("someLink").build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayContent();
    Assert.assertFalse(result);

  }

  @Test
  public void testIsDisplayImage_True() {
    NewsImageDTO image = NewsImageDTOBuilder.create().build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsImage(image).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayImage();
    Assert.assertTrue(result);

    result = displayBean.displayImage();
    Assert.assertTrue(result);

  }

  @Test
  public void testIsDisplayImage_False() {
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayImage();
    Assert.assertFalse(result);

    result = displayBean.displayImage();
    Assert.assertFalse(result);
  }

  @Test
  public void testGetImageHeight_No_Image() {

    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    int height = displayBean.getImageHeight();
    Assert.assertEquals(0, height);

  }

  @Test
  public void testGetImageHeight_With_Image() {
    NewsImageDTO image = NewsImageDTOBuilder.create().height(500).build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsImage(image).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    int height = displayBean.getImageHeight();
    Assert.assertEquals(500, height);
  }

  @Test
  public void testGetImageWidth_No_Image() {

    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    int result = displayBean.getImageWidth();
    Assert.assertEquals(0, result);

  }

  @Test
  public void testGetImageWidth_With_Image() {
    NewsImageDTO image = NewsImageDTOBuilder.create().width(500).build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsImage(image).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    int result = displayBean.getImageWidth();
    Assert.assertEquals(500, result);
  }

  @Test
  public void testGetAlt_No_Image() {

    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getAlt();
    Assert.assertEquals("", result);

  }

  @Test
  public void testGetAlt_With_Image() {
    NewsImageDTO image = NewsImageDTOBuilder.create().alt("alt").build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsImage(image).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getAlt();
    Assert.assertEquals("alt", result);
  }

  @Test
  public void testGetLegend_No_Image() {

    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getLegend();
    Assert.assertEquals("", result);

  }

  @Test
  public void testGetLegend_With_Image() {
    NewsImageDTO image = NewsImageDTOBuilder.create().legend("legend").build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsImage(image).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getLegend();
    Assert.assertEquals("legend", result);
  }

  @Test
  public void testGetSrcNo_Image() {

    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getImage();
    Assert.assertEquals("", result);

  }

  @Test
  public void testGetSrc_With_Image() {
    NewsImageDTO image = NewsImageDTOBuilder.create().src("src").build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsImage(image).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getImage();
    Assert.assertEquals(imageDisplaySrc + "src", result);
  }

  @Test
  public void testGetContent_No_Content() {

    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getContent();
    Assert.assertEquals("", result);

  }

  @Test
  public void testGetContent_With_Content() {
    NewsContentDTO content = NewsContentDTOBuilder.create().content("content").build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getContent();
    Assert.assertEquals("content", result);
  }

  @Test
  public void testGetTitle() {
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().title("title").build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getTitle();
    Assert.assertEquals("title", result);
  }

  @Test
  public void testGetPublicationDate() {

    LocalDate publicationDate = LocalDate.of(2017, 9, 10);
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().creationDate(publicationDate).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getPublicationDate();
    Assert.assertEquals("10/09/17", result);

  }

  @Test
  public void testGetPanelHeading() {

    String SPACE = " ";
    LocalDate publicationDate = LocalDate.of(2017, 9, 10);
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().author("Test").creationDate(publicationDate).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String panelHeadingBuilder = labelPar + SPACE + "Test" + SPACE + labelLe + SPACE + "10/09/17";

    String result = displayBean.getPanelHeading();
    Assert.assertEquals(panelHeadingBuilder, result);

  } 

  @Test
  public void testGetLinkUrl()  {
    NewsContentDTO content = NewsContentDTOBuilder.create().linkUrl("link").build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getLinkUrl();
    Assert.assertEquals("link", result);
  }

  @Test
  public void testGetVideoUrl()  {
    NewsContentDTO content = NewsContentDTOBuilder.create().videoUrl("video").build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getVideoUrl();
    Assert.assertEquals("video", result);
  }

  @Test
  public void testIsDisplayLink_True()  {
    NewsContentDTO content = NewsContentDTOBuilder.create().linkUrl("link").build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayLink();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsDisplayLink_False_No_Content()  {
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayLink();
    Assert.assertFalse(result);
  }

  @Test
  public void testIsDisplayLink_False_No_Link()  {
    NewsContentDTO content = NewsContentDTOBuilder.create().build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayLink();
    Assert.assertFalse(result);
  }

  @Test
  public void testIsDisplayVideo()  {
    NewsContentDTO content = NewsContentDTOBuilder.create().videoUrl("video").build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayVideo();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsDisplayVideo_False_No_Content()  {
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayVideo();
    Assert.assertFalse(result);
  }

  @Test
  public void testIsDisplayVideo_False_No_Video()  {
    NewsContentDTO content = NewsContentDTOBuilder.create().build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsContent(content).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    boolean result = displayBean.isDisplayVideo();
    Assert.assertFalse(result);
  }

  @Test
  public void testGetNewsEntryModifyHref()  {
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().id(123456789L).build();
    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(entry, imageDisplaySrc, labelPar, labelLe, dateFormat);

    String result = displayBean.getNewsEntryModifyHref();

    Assert.assertEquals("/manager/news/123456789", result);
  }

}
