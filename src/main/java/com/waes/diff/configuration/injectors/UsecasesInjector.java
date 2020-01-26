package com.waes.diff.configuration.injectors;

import com.google.inject.AbstractModule;
import com.waes.diff.core.usecases.*;
import com.waes.diff.core.usecases.impl.*;

/**
 * Registers usecases
 */
public class UsecasesInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(UploadContent.class).to(DefaultUploadContent.class);
        bind(DecodeContent.class).to(DefaultDecodeContent.class);
        bind(GetContent.class).to(DefaultGetContent.class);
        bind(ProcessDiff.class).to(DefaultProcessDiff.class);
        bind(SaveContent.class).to(DefaultSaveContent.class);
        bind(UpdateContent.class).to(DefaultUpdateContent.class);
    }
}
