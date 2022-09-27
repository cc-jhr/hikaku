package io.github.ccjhr.hikaku.converters.openapi.extensions

import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.PathItem
import io.github.ccjhr.hikaku.endpoints.HttpMethod
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.hikaku.endpoints.HttpMethod.POST
import io.github.ccjhr.hikaku.endpoints.HttpMethod.PATCH
import io.github.ccjhr.hikaku.endpoints.HttpMethod.DELETE
import io.github.ccjhr.hikaku.endpoints.HttpMethod.PUT
import io.github.ccjhr.hikaku.endpoints.HttpMethod.OPTIONS
import io.github.ccjhr.hikaku.endpoints.HttpMethod.HEAD
import io.github.ccjhr.hikaku.endpoints.HttpMethod.TRACE

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
