package com.waes.diff.configuration.injectors;

import com.google.inject.AbstractModule;
import com.waes.diff.entrypoints.GetDiffEndpoint;
import com.waes.diff.entrypoints.SaveContentEndpoint;
import com.waes.diff.entrypoints.impl.DefaultGetDiffEndpoint;
import com.waes.diff.entrypoints.impl.DefaultSaveContentEndpoint;

/**
 * Register endpoints
 */
public class EndpointsInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(SaveContentEndpoint.class).to(DefaultSaveContentEndpoint.class);
        bind(GetDiffEndpoint.class).to(DefaultGetDiffEndpoint.class);
    }

}
