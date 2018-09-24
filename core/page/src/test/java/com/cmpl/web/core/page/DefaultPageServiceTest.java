package com.cmpl.web.core.page;

import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.models.Page;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Arrays;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPageServiceTest {

    @Mock
    private PageMapper mapper;

    @Mock
    private PageDAO pageDAO;

    @Mock
    private FileService fileService;

    @Mock
    private SpringTemplateEngine templateEngine;

    @Spy
    @InjectMocks
    private DefaultPageService pageService;

    @Test
    public void testCreateEntity() throws Exception {
        PageDTO dtoToCreate = PageDTOBuilder.create().body("someBody").footer("someFooter")
                .header("someHeader")
                .name("someName").build();
        Page entityToCreate = PageBuilder.create().build();

        BDDMockito.doReturn(dtoToCreate).when(mapper).toDTO(BDDMockito.any(Page.class));
        BDDMockito.doReturn(entityToCreate).when(mapper).toEntity(BDDMockito.any(PageDTO.class));
        BDDMockito.given(pageDAO.createEntity(BDDMockito.any(Page.class))).willReturn(entityToCreate);
        BDDMockito.doNothing().when(fileService)
                .saveFileOnSystem(BDDMockito.anyString(), BDDMockito.anyString());

        pageService.createEntity(dtoToCreate, Locale.FRANCE.getLanguage());

        BDDMockito.verify(fileService, BDDMockito.times(3)).saveFileOnSystem(BDDMockito.anyString(),
                BDDMockito.anyString());

    }

    @Test
    public void testUpdateEntity() throws Exception {

        PageDTO dtoToUpdate = PageDTOBuilder.create().body("someBody").footer("someFooter")
                .header("someHeader")
                .name("someName").build();
        Page entityToUpdate = PageBuilder.create().build();

        BDDMockito.doReturn(dtoToUpdate).when(mapper).toDTO(BDDMockito.any(Page.class));
        BDDMockito.doReturn(entityToUpdate).when(mapper).toEntity(BDDMockito.any(PageDTO.class));
        BDDMockito.given(pageDAO.updateEntity(BDDMockito.any(Page.class))).willReturn(entityToUpdate);
        BDDMockito.doNothing().when(fileService)
                .saveFileOnSystem(BDDMockito.anyString(), BDDMockito.anyString());

        pageService.updateEntity(dtoToUpdate, Locale.FRANCE.getLanguage());

        BDDMockito.verify(fileService, BDDMockito.times(3)).saveFileOnSystem(BDDMockito.anyString(),
                BDDMockito.anyString());
    }

    @Test
    public void testGetEntity() throws Exception {

        PageDTO dtoToFind = PageDTOBuilder.create().name("someName").build();
        Page entityToFind = PageBuilder.create().build();
        BDDMockito.doReturn(dtoToFind).when(mapper).toDTO(BDDMockito.any(Page.class));
        BDDMockito.given(pageDAO.getEntity(BDDMockito.anyLong())).willReturn(entityToFind);

        String content = "someContent";
        BDDMockito.given(fileService.readFileContentFromSystem(BDDMockito.anyString()))
                .willReturn(content);

        PageDTO result = pageService.getEntity(123456789l, Locale.FRANCE.getLanguage());
        Assert.assertEquals(content, result.getBody());
        Assert.assertEquals(content, result.getHeader());
        Assert.assertEquals(content, result.getFooter());

        BDDMockito.verify(fileService, BDDMockito.times(5))
                .readFileContentFromSystem(BDDMockito.anyString());

    }


    @Test
    public void testGetPages() throws Exception {
        PageDTO result = PageDTOBuilder.create().id(123456789l).build();
        Page page = PageBuilder.create().build();

        BDDMockito.given(pageDAO.getPages(BDDMockito.any(Sort.class))).willReturn(Arrays.asList(page));
        BDDMockito.doReturn(Arrays.asList(result)).when(mapper).toListDTO(BDDMockito.anyList());

        Assert.assertEquals(result, pageService.getPages().get(0));

    }

}
