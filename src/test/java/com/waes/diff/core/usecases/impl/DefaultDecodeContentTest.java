package com.waes.diff.core.usecases.impl;

import com.waes.diff.core.usecases.DecodeContent;
import com.waes.diff.core.usecases.UseCaseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultDecodeContentTest {

    private DefaultDecodeContent instance;

    @Before
    public void setup() {
        instance = new DefaultDecodeContent();
    }

    @Test(expected = UseCaseException.class)
    public void TestDecodeWithoutModel() {
        instance.apply(null);
    }

    @Test(expected = UseCaseException.class)
    public void TestDecodeWithBlankContent() {
        instance.apply(DecodeContent.Model.builder().content("").build());
    }

    @Test
    public void TestDecodeContent() {
        String encodedContent = "some encoded content";
        String decodedContent = instance.apply(DecodeContent.Model.builder().content(encodedContent).build());
        Assert.assertNotEquals(encodedContent, decodedContent);
    }


}
