package com.waes.diff.core.usecases.impl;

import com.waes.diff.core.model.Content;
import com.waes.diff.core.repositories.SaveContentRepository;
import com.waes.diff.core.usecases.SaveContent;
import com.waes.diff.core.usecases.UseCaseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DefaultSaveContentTest {

    private SaveContentRepository saveContentRepository;
    private DefaultSaveContent instance;

    @Before
    public void setup() {
        this.saveContentRepository = Mockito.mock(SaveContentRepository.class);
        this.instance = new DefaultSaveContent(saveContentRepository);
    }

    @Test(expected = UseCaseException.class)
    public void TestSaveContentWithoutModel() {
        instance.apply(null);
    }

    @Test(expected = UseCaseException.class)
    public void TestSaveContentWithoutContent() {
        instance.apply(SaveContent.Model.builder().content(null).build());
    }

    @Test
    public void TestSaveContent() {
        Content c = new Content();
        instance.apply(SaveContent.Model.builder().content(c).build());
        Mockito.verify(saveContentRepository, Mockito.times(1)).save(c);
    }

}
