package com.waes.diff.core.usecases.impl;

import com.waes.diff.core.model.Content;
import com.waes.diff.core.repositories.UpdateContentRepository;
import com.waes.diff.core.usecases.UpdateContent;
import com.waes.diff.core.usecases.UseCaseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DefaultUpdateContentTest {

    private UpdateContentRepository updateContentRepository;
    private DefaultUpdateContent instance;

    @Before
    public void setup() {
        this.updateContentRepository = Mockito.mock(UpdateContentRepository.class);
        instance = new DefaultUpdateContent(updateContentRepository);
    }

    @Test(expected = UseCaseException.class)
    public void TestUpdateContentWithoutModel() {
        instance.apply(null);
    }

    @Test(expected = UseCaseException.class)
    public void TestUpdateContentWithoutContent() {
        instance.apply(UpdateContent.Model.builder().content(null).build());
    }

    @Test
    public void TestUpdateContent() {
        Content c = new Content();
        instance.apply(UpdateContent.Model.builder().content(c).build());
        Mockito.verify(updateContentRepository, Mockito.times(1)).update(c);
    }
}
