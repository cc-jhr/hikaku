package io.github.ccjhr.hikaku.converters.openapi.extensions

import io.github.ccjhr.hikaku.endpoints.HttpMethod
import io.github.ccjhr.hikaku.endpoints.HttpMethod.*
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.PathItem

internal fun PathItem.httpMethods() = mapOf<HttpMethod, Operation?>(
    GET to get,
    POST to post,
    PATCH to patch,
    DELETE to delete,
    PUT to put,
    OPTIONS to options,
    HEAD to head,
    TRACE to trace
).filterValues { it != null }
