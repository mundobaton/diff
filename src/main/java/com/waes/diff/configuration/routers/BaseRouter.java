package com.waes.diff.configuration.routers;

import spark.Request;
import spark.Response;

/**
 * This is our base router, routers must implement this interface to match an url pattern
 * @param <T>
 */
public interface BaseRouter<T> {
    T execute(Request request, Response response);
}
