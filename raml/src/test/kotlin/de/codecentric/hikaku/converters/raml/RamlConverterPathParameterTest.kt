package de.codecentric.hikaku.converters.raml

import de.codecentric.hikaku.endpoints.Endpoint
import de.codecentric.hikaku.endpoints.HttpMethod.GET
import de.codecentric.hikaku.endpoints.HttpMethod.POST
import de.codecentric.hikaku.endpoints.PathParameter
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.io.path.toPath
import kotlin.test.Test

class RamlConverterPathParameterTest {

    @Test
    fun `simple path parameter declaration`() {
        // given
        val file = this::class.java.classLoader.getResource("path_parameter/simple_path_parameter.raml").toURI().toPath()

        val specification = setOf(
            Endpoint(
                path = "/todos/{id}",
                httpMethod = GET,
                pathParameters = setOf(
                    PathParameter("id"),
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

    @Test
    fun `nested path parameter declaration`() {
        // given
        val file = this::class.java.classLoader.getResource("path_parameter/nested_path_parameter.raml").toURI().toPath()

        val specification = setOf(
            Endpoint("/todos", POST),
            Endpoint(
                path = "/todos/{id}",
                httpMethod = GET,
                pathParameters = setOf(
                    PathParameter("id"),
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