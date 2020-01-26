package com.waes.diff.configuration.injectors;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.waes.diff.configuration.routers.BaseRouter;
import com.waes.diff.configuration.routers.impl.DefaultGetDiffRouter;
import com.waes.diff.configuration.routers.impl.DefaultSaveContentRouter;
import com.waes.diff.core.dtos.GetDiffDto;
import com.waes.diff.core.dtos.SaveContentDto;
import com.waes.diff.core.usecases.impl.DefaultGetContent;

/**
 * Registers routers
 */
public class RoutersInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<BaseRouter<SaveContentDto>>() {}).to(new TypeLiteral<DefaultSaveContentRouter>() {});
        bind(new TypeLiteral<BaseRouter<GetDiffDto>>() {}).to(new TypeLiteral<DefaultGetDiffRouter>() {});
    }
}
