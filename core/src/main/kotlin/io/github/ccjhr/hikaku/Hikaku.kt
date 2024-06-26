package io.github.ccjhr.hikaku

import io.github.ccjhr.hikaku.SupportedFeatures.Feature
import io.github.ccjhr.hikaku.converters.EndpointConverter
import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.reporters.MatchResult
import io.github.ccjhr.hikaku.reporters.Reporter
import kotlin.test.fail

/**
 * Entry point for writing a hikaku test. Provide the [EndpointConverter]s and call [match] to test if the specification and the implementation of your REST-API match.
 * @param specification An [EndpointConverter] which converts your specification for the equality check.
 * @param implementation An [EndpointConverter]  which converts your implementation for the equality check.
 * @param config The configuration is optional. It lets you control the matching.
 */
class Hikaku(
    private val specification: EndpointConverter,
    private val implementation: EndpointConverter,
    var config: HikakuConfig = HikakuConfig(),
) {
    private val supportedFeatures = SupportedFeatures(specification.supportedFeatures.intersect(implementation.supportedFeatures))

    private fun Set<Endpoint>.applyConfig(config: HikakuConfig): List<Endpoint> {
        val result = this.toMutableList()

        config.filters.forEach {
            result.removeAll(this.filter(it))
        }

        return result
    }

    private fun reportResult(matchResult: MatchResult) {
        config.reporters.forEach { it.report(matchResult) }
    }

    /**
     * Calling this method creates a [MatchResult]. It will be passed to the [Reporter] defined in the configuration and call [assert] with the end result.
     */
    fun match() {
        val specificationEndpoints = specification
            .conversionResult
            .applyConfig(config)
            .toSet()

        val implementationEndpoints = implementation
            .conversionResult
            .applyConfig(config)
            .toSet()

        val notExpected = implementationEndpoints.toMutableSet()
        val notFound = specificationEndpoints.toMutableSet()

        specificationEndpoints.forEach { currentEndpoint ->
            if (iterableContains(notExpected, currentEndpoint)) {
                notExpected.removeIf(endpointMatches(currentEndpoint))
                notFound.removeIf(endpointMatches(currentEndpoint))
            }
        }

        reportResult(
            MatchResult(
                supportedFeatures,
                specificationEndpoints,
                implementationEndpoints,
                notFound,
                notExpected
            )
        )

        if (notExpected.isNotEmpty() || notFound.isNotEmpty()) {
            fail("Implementation does not match specification.")
        }
    }

    private fun endpointMatches(otherEndpoint: Endpoint): (Endpoint) -> Boolean {
        return {
            var matches = true
            matches = matches && it.path == otherEndpoint.path
            matches = matches && it.httpMethod == otherEndpoint.httpMethod

            supportedFeatures.forEach { feature ->
                matches = when (feature) {
                    Feature.QueryParameters -> matches && it.queryParameters == otherEndpoint.queryParameters
                    Feature.PathParameters -> matches && it.pathParameters == otherEndpoint.pathParameters
                    Feature.HeaderParameters -> matches && it.headerParameters == otherEndpoint.headerParameters
                    Feature.MatrixParameters -> matches && it.matrixParameters == otherEndpoint.matrixParameters
                    Feature.Produces -> matches && it.produces == otherEndpoint.produces
                    Feature.Consumes -> matches && it.consumes == otherEndpoint.consumes
                    Feature.Deprecation -> matches && it.deprecated == otherEndpoint.deprecated
                }
            }

            matches
        }
    }

    private fun iterableContains(notExpected: Set<Endpoint>, value: Endpoint) = notExpected.any(endpointMatches(value))
}