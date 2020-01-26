package com.waes.diff.core.usecases.impl;

import com.waes.diff.core.model.Content;
import com.waes.diff.core.usecases.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Optional;

public class DefaultUploadContentTest {

    private GetContent getContent;
    private SaveContent saveContent;
    private UpdateContent updateContent;
    private ProcessDiff processDiff;
    private DefaultUploadContent instance;

    @Before
    public void setup() {
        this.getContent = Mockito.mock(GetContent.class);
        this.saveContent = Mockito.mock(SaveContent.class);
        this.updateContent = Mockito.mock(UpdateContent.class);
        this.processDiff = Mockito.mock(ProcessDiff.class);
        this.instance = new DefaultUploadContent(getContent, saveContent, updateContent, processDiff);
    }

    @Test(expected = UseCaseException.class)
    public void TestUploadContentWithoutModel() {
        instance.apply(null);
    }

    @Test(expected = UseCaseException.class)
    public void TestUploadContentWithoutId() {
        instance.apply(UploadContent.Model.builder().side("left").data("some content").build());
    }

    @Test(expected = UseCaseException.class)
    public void TestUploadContentWithInvalidId() {
        instance.apply(UploadContent.Model.builder().id(-1L).side("left").data("some content").build());
    }

    @Test(expected = UseCaseException.class)
    public void TestUploadContentWithoutSide() {
        instance.apply(UploadContent.Model.builder().id(1L).data("some content").build());
    }

    @Test(expected = UseCaseException.class)
    public void TestUploadContentWithInvalidSide() {
        instance.apply(UploadContent.Model.builder().id(1L).side("some side").data("some content").build());
    }

    @Test(expected = UseCaseException.class)
    public void TestUploadContentWithoutData() {
        instance.apply(UploadContent.Model.builder().id(1L).side("left").build());
    }

    @Test
    public void TestUploadContentWithNewContentLeft() {
        Mockito.when(getContent.apply(ArgumentMatchers.any())).thenReturn(Optional.empty());
        String result = instance.apply(UploadContent.Model.builder().id(1L).side("left").data("some data").build());

        Mockito.verify(saveContent, Mockito.times(1)).apply(ArgumentMatchers.any());
        Mockito.verify(updateContent, Mockito.times(0)).apply(ArgumentMatchers.any());
        Mockito.verify(processDiff, Mockito.times(0)).apply(ArgumentMatchers.any());
        Assert.assertNotNull(result);
        Assert.assertEquals("content saved successfully", result);
    }

    @Test
    public void TestUploadContentWithNewContentRight() {
        Mockito.when(getContent.apply(ArgumentMatchers.any())).thenReturn(Optional.empty());
        String result = instance.apply(UploadContent.Model.builder().id(1L).side("right").data("some data").build());

        Mockito.verify(saveContent, Mockito.times(1)).apply(ArgumentMatchers.any());
        Mockito.verify(updateContent, Mockito.times(0)).apply(ArgumentMatchers.any());
        Mockito.verify(processDiff, Mockito.times(0)).apply(ArgumentMatchers.any());
        Assert.assertNotNull(result);
        Assert.assertEquals("content saved successfully", result);
    }

    @Test(expected = ContentAlreadyExistException.class)
    public void TestUploadContentExistingLeftSideContent() {
        Content c = new Content();
        c.setLeftContent("some content");
        Mockito.when(getContent.apply(ArgumentMatchers.any())).thenReturn(Optional.of(c));
        instance.apply(UploadContent.Model.builder().id(1L).side("left").data("some data").build());

        Mockito.verify(saveContent, Mockito.times(0)).apply(ArgumentMatchers.any());
        Mockito.verify(updateContent, Mockito.times(0)).apply(ArgumentMatchers.any());
        Mockito.verify(processDiff, Mockito.times(0)).apply(ArgumentMatchers.any());
    }

    @Test(expected = ContentAlreadyExistException.class)
    public void TestUploadContentExistingRightSideContent() {
        Content c = new Content();
        c.setRightContent("some content");
        Mockito.when(getContent.apply(ArgumentMatchers.any())).thenReturn(Optional.of(c));
        instance.apply(UploadContent.Model.builder().id(1L).side("right").data("some data").build());

        Mockito.verify(saveContent, Mockito.times(0)).apply(ArgumentMatchers.any());
        Mockito.verify(updateContent, Mockito.times(0)).apply(ArgumentMatchers.any());
        Mockito.verify(processDiff, Mockito.times(0)).apply(ArgumentMatchers.any());
    }

    @Test
    public void TestUploadContentLeftSideContent() throws Exception {
        Content c = new Content();
        c.setLeftContent("some content");
        Mockito.when(getContent.apply(ArgumentMatchers.any())).thenReturn(Optional.of(c));
        instance.apply(UploadContent.Model.builder().id(1L).side("right").data("some data").build());

        Thread.sleep(1000);
        Mockito.verify(saveContent, Mockito.times(0)).apply(ArgumentMatchers.any());
        Mockito.verify(updateContent, Mockito.times(1)).apply(ArgumentMatchers.any());
        Mockito.verify(processDiff, Mockito.times(1)).apply(ArgumentMatchers.any());
    }

    @Test
    public void TestUploadContentRightSideContent() throws Exception {
        Content c = new Content();
        c.setRightContent("some content");
        Mockito.when(getContent.apply(ArgumentMatchers.any())).thenReturn(Optional.of(c));
        instance.apply(UploadContent.Model.builder().id(1L).side("left").data("some data").build());

        Thread.sleep(1000);
        Mockito.verify(saveContent, Mockito.times(0)).apply(ArgumentMatchers.any());
        Mockito.verify(updateContent, Mockito.times(1)).apply(ArgumentMatchers.any());
        Mockito.verify(processDiff, Mockito.times(1)).apply(ArgumentMatchers.any());
    }

}
