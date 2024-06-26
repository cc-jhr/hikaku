package io.github.ccjhr.hikaku.converters.openapi

import io.github.ccjhr.hikaku.SupportedFeatures
import io.github.ccjhr.hikaku.SupportedFeatures.Feature
import io.github.ccjhr.hikaku.converters.AbstractEndpointConverter
import io.github.ccjhr.hikaku.converters.EndpointConverterException
import io.github.ccjhr.hikaku.converters.openapi.extensions.httpMethods
import io.github.ccjhr.hikaku.converters.openapi.extractors.*
import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod
import io.github.ccjhr.hikaku.extensions.checkFileValidity
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.parser.OpenAPIV3Parser
import java.io.File
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.Files.readAllLines
import java.nio.file.Path

/**
 * Extracts and converts [Endpoint]s from OpenAPI 3.0.X document. Either a *.yaml*, *.yml* or a *.json* file.
 */
class OpenApiConverter private constructor(private val specificationContent: String) : AbstractEndpointConverter() {

    @JvmOverloads
    constructor(openApiSpecification: File, charset: Charset = UTF_8) : this(openApiSpecification.toPath(), charset)

    @JvmOverloads
    constructor(openApiSpecification: Path, charset: Charset = UTF_8) : this(
        readFileContent(
            openApiSpecification,
            charset
        )
    )

    override val supportedFeatures = SupportedFeatures(
        Feature.QueryParameters,
        Feature.PathParameters,
        Feature.HeaderParameters,
        Feature.Produces,
        Feature.Consumes,
        Feature.Deprecation
    )

    override fun convert(): Set<Endpoint> {
        try {
            return parseOpenApi()
        } catch (throwable: Throwable) {
            throw EndpointConverterException(throwable)
        }
    }

    private fun parseOpenApi(): Set<Endpoint> {
        val swaggerParseResult = OpenAPIV3Parser().readContents(specificationContent, null, null)

        val openApi = swaggerParseResult.openAPI ?: throw openApiParseException(swaggerParseResult.messages)

        val extractConsumesMediaTypes = ConsumesExtractor(openApi)
        val extractProduceMediaTypes = ProducesExtractor(openApi)
        val extractQueryParameters = QueryParameterExtractor(openApi)
        val extractHeaderParameters = HeaderParameterExtractor(openApi)
        val extractPathParameters = PathParameterExtractor(openApi)

        return openApi.paths.flatMap { (path, pathItem) ->
            val commonQueryParameters = extractQueryParameters(pathItem.parameters)
            val commonPathParameters = extractPathParameters(pathItem.parameters)
            val commonHeaderParameters = extractHeaderParameters(pathItem.parameters)

            pathItem.httpMethods().map { (httpMethod: HttpMethod, operation: Operation?) ->
                Endpoint(
                    path = path,
                    httpMethod = httpMethod,
                    queryParameters = commonQueryParameters.union(extractQueryParameters(operation?.parameters)),
                    pathParameters = commonPathParameters.union(extractPathParameters(operation?.parameters)),
                    headerParameters = commonHeaderParameters.union(extractHeaderParameters(operation?.parameters)),
                    consumes = extractConsumesMediaTypes(operation),
                    produces = extractProduceMediaTypes(operation),
                    deprecated = operation?.deprecated ?: false
                )
            }
        }
            .toSet()
    }
}

private fun readFileContent(openApiSpecification: Path, charset: Charset): String {
    try {
        openApiSpecification.checkFileValidity(".json", ".yaml", ".yml")
    } catch (throwable: Throwable) {
        throw EndpointConverterException(throwable)
    }
    val fileContent = readAllLines(openApiSpecification, charset).joinToString("\n")

    if (fileContent.isBlank()) {
        throw EndpointConverterException("Given OpenAPI file is blank.")
    }

    return fileContent
}

private fun openApiParseException(reasons: List<String>) =
    EndpointConverterException("Failed to parse OpenAPI spec. Reasons:\n${reasons.joinToString("\n")}")