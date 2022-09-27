package io.github.ccjhr.hikaku.converters

import io.github.ccjhr.hikaku.endpoints.Endpoint

/**
 * Abstract [EndpointConverter] which triggers conversion when accessing the [conversionResult]s.
 */
abstract class AbstractEndpointConverter : EndpointConverter {

    override val conversionResult: Set<Endpoint> by lazy {
        this.convert()
    }

    abstract fun convert(): Set<Endpoint>
}