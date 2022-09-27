package io.github.ccjhr.hikaku.converters.openapi.extractors

import io.github.ccjhr.hikaku.converters.openapi.extensions.referencedSchema
import io.github.ccjhr.hikaku.endpoints.PathParameter
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.parameters.Parameter as OpenApiParameter
import io.swagger.v3.oas.models.parameters.PathParameter as OpenApiPathParameter

internal class PathParameterExtractor(private val openApi: OpenAPI) {

    operator fun invoke(parameters: List<OpenApiParameter>?): Set<PathParameter> {
        return extractInlinePathParameters(parameters).union(extractPathParametersFromComponents(parameters))
    }

    private fun extractInlinePathParameters(parameters: List<OpenApiParameter>?): Set<PathParameter> {
        return parameters
            ?.filterIsInstance<OpenApiPathParameter>()
            ?.map { PathParameter(it.name) }
            .orEmpty()
            .toSet()
    }

    private fun extractPathParametersFromComponents(parameters: List<OpenApiParameter>?): Set<PathParameter> {
        return parameters
            ?.filter { it.referencedSchema != null }
            ?.map {
                Regex("#/components/parameters/(?<key>.+)")
                    .find(it.referencedSchema)
                    ?.groups
                    ?.get("key")
                    ?.value
            }
            ?.map {
                openApi.components
                    .parameters[it]
            }
            ?.filter { it?.`in` == "path" }
            ?.map { PathParameter(it?.name ?: "") }
            .orEmpty()
            .toSet()
    }
}