package com.waes.diff.core.usecases.impl;

import com.waes.diff.core.model.Content;
import com.waes.diff.core.repositories.FindContentRepository;
import com.waes.diff.core.usecases.GetContent;
import com.waes.diff.core.usecases.UseCaseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class DefaultGetContentTest {

    private FindContentRepository findContentRepository;
    private DefaultGetContent instance;

    @Before
    public void setup() {
        findContentRepository = Mockito.mock(FindContentRepository.class);
        instance = new DefaultGetContent(findContentRepository);
    }

    @Test(expected = UseCaseException.class)
    public void TestGetContentWithoutModel() {
        instance.apply(null);
    }

    @Test(expected = UseCaseException.class)
    public void TestGetContentWithNullContentId() {
        instance.apply(GetContent.Model.builder().id(null).build());
    }

    @Test(expected = UseCaseException.class)
    public void TestGetContentWithInvalidContentId() {
        instance.apply(GetContent.Model.builder().id(-1L).build());
    }

    @Test
    public void TestGetContentNotFoundContent() {
        Long contentId = 1L;
        Mockito.when(findContentRepository.find(contentId)).thenReturn(Optional.empty());

        Optional<Content> result = instance.apply(GetContent.Model.builder().id(contentId).build());
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void TestGetContent() {
        Long contentId = 1L;
        com.waes.diff.core.entities.Content c = new com.waes.diff.core.entities.Content();
        c.setContentId(contentId);

        Mockito.when(findContentRepository.find(contentId)).thenReturn(Optional.of(c));
        Optional<Content> result = instance.apply(GetContent.Model.builder().id(contentId).build());
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(contentId, result.get().getContentId());
    }

}
