package com.waes.diff.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.waes.diff.configuration.injectors.*;
import com.waes.diff.configuration.routers.BaseRouter;
import com.waes.diff.configuration.routers.impl.ErrorHandlerRouter;
import com.waes.diff.core.dtos.GetDiffDto;
import com.waes.diff.core.dtos.SaveContentDto;
import lombok.extern.slf4j.Slf4j;
import spark.ResponseTransformer;
import spark.Route;

import static spark.Spark.*;

/**
 * Main class responsible of http routers initialization, wiring and database initialization
 */

@Slf4j
public class Main {

    private static final int DEFAULT_PORT = 8080;
    public static final String APP = "app";
    private static final String DEFAULT_CONTENT_TYPE = "application/json";

    private ResponseTransformer responseTransformer;

    public static void main(String[] args) {
        new Main().init();
    }

    private void init() {
        configureServer();
        configureInjectors();
        configureDependencies();
        configureRoutes();
    }

    /**
     * Sets some basic defaults such as port and default response content type
     */
    private void configureServer() {
        port(DEFAULT_PORT);

        after((request, response) -> {
            response.type(DEFAULT_CONTENT_TYPE);
        });
    }

    /**
     * Register modules like routers/entrypoints/usecases and repositories
     */
    private void configureInjectors() {
        Config.addInjector(APP, Guice.createInjector(
                new RoutersInjector(),
                new EndpointsInjector(),
                new UtilsInjector(),
                new UsecasesInjector(),
                new DatabaseInjector(),
                new RepositoriesInjector()
        ));
    }

    private void configureDependencies() {
        this.responseTransformer = new JsonTransformer(Config.getInjector(APP).getInstance(ObjectMapper.class));
    }

    /**
     * Register routes with its handlers
     */
    private void configureRoutes() {
        Config.getInjector(APP).getInstance(ErrorHandlerRouter.class).register();

        path("/v1/diff", () -> {
            put("/:id/:side", this.buildRoute(Key.get(new TypeLiteral<BaseRouter<SaveContentDto>>() {
            })), this.responseTransformer);

            get("/:id", this.buildRoute(Key.get(new TypeLiteral<BaseRouter<GetDiffDto>>() {
            })), this.responseTransformer);
        });
    }

    /**
     * Build a new route for each request.
     *
     * @param key
     * @param <T> Type that needs to extend a Base Router.
     * @return
     */
    private <T extends BaseRouter> Route buildRoute(Key<T> key) {
        log.debug("get new instance of " + key.getClass().getName());
        return (request, response) -> Config.getInjector(APP).getInstance(key).execute(request, response);
    }
}
