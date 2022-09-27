package io.github.ccjhr.hikaku.converters.openapi

import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.*
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.io.path.toPath
import kotlin.test.Test

class OpenApiConverterEndpointTest {

    @Test
    fun `extract two different paths`() {
        //given
        val file = this::class.java.classLoader.getResource("endpoints/endpoints_two_different_paths.yaml").toURI().toPath()
        val implementation = setOf(
            Endpoint("/todos", GET),
            Endpoint("/tags", GET),
        )

        //when
        val specification = OpenApiConverter(file).conversionResult

        //then
        specification mustSatisfy {
            it containsExactly implementation
        }
    }

    @Test
    fun `extract two paths of which one is nested`() {
        //given
        val file = this::class.java.classLoader.getResource("endpoints/endpoints_two_nested_paths.yaml").toURI().toPath()
        val implementation = setOf(
            Endpoint("/todos", GET),
            Endpoint("/todos/query", GET),
        )

        //when
        val specification = OpenApiConverter(file).conversionResult

        //then
        specification mustSatisfy {
            it containsExactly implementation
        }
    }

    @Test
    fun `extract all http methods`() {
        //given
        val file = this::class.java.classLoader.getResource("endpoints/endpoints_all_http_methods.yaml").toURI().toPath()
        val implementation = setOf(
            Endpoint("/todos", GET),
            Endpoint("/todos", POST),
            Endpoint("/todos", PUT),
            Endpoint("/todos", PATCH),
            Endpoint("/todos", DELETE),
            Endpoint("/todos", HEAD),
            Endpoint("/todos", OPTIONS),
            Endpoint("/todos", TRACE),
        )

        //when
        val specification = OpenApiConverter(file).conversionResult

        //then
        specification mustSatisfy {
            it containsExactly implementation
        }
    }
}