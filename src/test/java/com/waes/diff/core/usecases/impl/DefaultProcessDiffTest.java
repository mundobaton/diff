package com.waes.diff.core.usecases.impl;

import com.waes.diff.core.model.Content;
import com.waes.diff.core.usecases.DecodeContent;
import com.waes.diff.core.usecases.ProcessDiff;
import com.waes.diff.core.usecases.UpdateContent;
import com.waes.diff.core.usecases.UseCaseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class DefaultProcessDiffTest {

    private UpdateContent updateContent;
    private DecodeContent decodeContent;
    private DefaultProcessDiff instance;

    @Before
    public void setup() {
        updateContent = Mockito.mock(UpdateContent.class);
        decodeContent = Mockito.mock(DecodeContent.class);
        instance = new DefaultProcessDiff(updateContent, decodeContent);
    }

    @Test(expected = UseCaseException.class)
    public void TestProcessDiffWithoutModel() {
        instance.apply(null);
    }

    @Test(expected = UseCaseException.class)
    public void TestProcessDiffWithoutContent() {
        instance.apply(ProcessDiff.Model.builder().content(null).build());
    }

    @Test(expected = UseCaseException.class)
    public void TestProcessDiffWithoutLeftContent() {
        Content c = new Content();
        c.setRightContent("some content");
        instance.apply(ProcessDiff.Model.builder().content(c).build());
    }

    @Test(expected = UseCaseException.class)
    public void TestProcessDiffWithoutRightContent() {
        Content c = new Content();
        c.setLeftContent("some content");
        instance.apply(ProcessDiff.Model.builder().content(c).build());
    }

    @Test
    public void TestProcessDiffWithEqContents() {
        Content c = new Content();
        c.setLeftContent("some content");
        c.setRightContent("some content");

        Mockito.when(decodeContent.apply(ArgumentMatchers.any())).thenReturn("some content");

        instance.apply(ProcessDiff.Model.builder().content(c).build());
        Assert.assertEquals("left and right contents are equal", c.getResult());
        Mockito.verify(updateContent, Mockito.times(1)).apply(ArgumentMatchers.any());
    }

    @Test
    public void TestDoDiffDifferentLengths() {
        String result = instance.doDiff("some content", "another content");
        Assert.assertNotNull(result);
        Assert.assertEquals("left and right contents have different lengths", result);
    }

    @Test
    public void TestDoDiff1() {
        String t1 = "Hello world!";
        String t2 = "Wello horld?";
        String result = instance.doDiff(t1, t2);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("3 differences found"));
    }

    @Test
    public void TestDoDiff2() {
        String t1 = "Hello world!";
        String t2 = "Wello horld!";
        String result = instance.doDiff(t1, t2);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("2 differences found"));
    }

    @Test
    public void TestDoDiff3() {
        String t1 = "Hello world!";
        String t2 = "Wello world!";
        String result = instance.doDiff(t1, t2);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("1 differences found"));
    }

    @Test
    public void TestDoDiff4() {
        String t1 = "Hello world!";
        String t2 = "xxxxx world!";
        String result = instance.doDiff(t1, t2);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("1 differences found"));
        Assert.assertTrue(result.contains("length: 5"));
    }

}
