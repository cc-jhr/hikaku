package io.github.ccjhr.hikaku.converters

import io.github.ccjhr.hikaku.SupportedFeatures
import io.github.ccjhr.hikaku.SupportedFeatures.Feature
import io.github.ccjhr.hikaku.endpoints.Endpoint

/**
 * Converts either a specific type of specification or implementation into the internal hikaku format in order to be able to perform a matching on the extracted components.
 */
interface EndpointConverter {

    /** Result of the conversion containing all extracted [Endpoint]s. */
    val conversionResult: Set<Endpoint>

    /** List of [Feature]s that this [EndpointConverter]s supports. */
    val supportedFeatures: SupportedFeatures
}