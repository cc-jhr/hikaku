package de.codecentric.hikaku.converters.raml

import de.codecentric.hikaku.endpoints.Endpoint
import de.codecentric.hikaku.endpoints.HttpMethod.GET
import de.codecentric.hikaku.endpoints.QueryParameter
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.io.path.toPath
import kotlin.test.Test

class RamlConverterQueryParameterTest {

    @Test
    fun `extracts required and optional query parameter`() {
        // given
        val file = this::class.java.classLoader.getResource("query_parameter/query_parameter.raml").toURI().toPath()

        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                queryParameters = setOf(
                    QueryParameter("limit", true),
                    QueryParameter("tag", false),
                ),
            ),
        )

        // when
        val implementation = RamlConverter(file).conversionResult

        // then
        implementation mustSatisfy {
            it containsExactly specification
        }
    }
}