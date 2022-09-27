package de.codecentric.hikaku.converters.openapi

import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.DELETE
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.io.path.toPath
import kotlin.test.Test

class OpenApiConverterProducesTest {

    @Test
    fun `inline declaration`() {
        //given
        val file = this::class.java.classLoader.getResource("produces/produces_inline.yaml").toURI().toPath()
        val implementation = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf("application/json"),
            ),
        )

        //when
        val specification = OpenApiConverter(file).conversionResult

        //then
        specification mustSatisfy {
            it containsExactly implementation
        }
    }

    @Test
    fun `no content-type`() {
        //given
        val file = this::class.java.classLoader.getResource("produces/produces_no_content_type.yaml").toURI().toPath()
        val implementation = setOf(
            Endpoint("/todos", DELETE),
        )

        //when
        val specification = OpenApiConverter(file).conversionResult

        //then
        specification mustSatisfy {
            it containsExactly implementation
        }
    }

    @Test
    fun `response is declared in components section`() {
        //given
        val file = this::class.java.classLoader.getResource("produces/produces_response_in_components.yaml").toURI().toPath()
        val implementation = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf("application/xml"),
            ),
        )

        //when
        val specification = OpenApiConverter(file).conversionResult

        //then
        specification mustSatisfy {
            it containsExactly implementation
        }
    }

    @Test
    fun `produces having a default value`() {
        //given
        val file = this::class.java.classLoader.getResource("produces/produces_with_default.yaml").toURI().toPath()
        val implementation = setOf(
            Endpoint(
                path = "/todos/query",
                httpMethod = GET,
                produces = setOf(
                    "application/json",
                    "text/plain",
                ),
            ),
        )

        //when
        val specification = OpenApiConverter(file).conversionResult

        //then
        specification mustSatisfy {
            it containsExactly implementation
        }
    }
}