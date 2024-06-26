package io.github.ccjhr.hikaku.converters.wadl

import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.*
import io.github.ccjhr.mustSatisfy
import java.nio.file.Paths
import kotlin.test.Test

class WadlConverterEndpointTest {

    @Test
    fun `extract two different paths`() {
        // given
        val file = Paths.get(this::class.java.classLoader.getResource("endpoints/endpoints_two_different_paths.wadl").toURI())
        val implementation: Set<Endpoint> = setOf(
            Endpoint("/todos", GET),
            Endpoint("/tags", GET),
        )

        // when
        val specification = WadlConverter(file)

        // then
        specification.conversionResult mustSatisfy {
            it containsExactly implementation
        }
    }

    @Test
    fun `extract two nested paths`() {
        // given
        val file = Paths.get(this::class.java.classLoader.getResource("endpoints/endpoints_two_nested_paths.wadl").toURI())
        val implementation: Set<Endpoint> = setOf(
            Endpoint("/todos", GET),
            Endpoint("/todos/{id}", GET),
        )

        // when
        val specification = WadlConverter(file)

        // then
        specification.conversionResult mustSatisfy {
            it containsExactly implementation
        }
    }

    @Test
    fun `extract all http methods`() {
        // given
        val file = Paths.get(this::class.java.classLoader.getResource("endpoints/endpoints.wadl").toURI())
        val implementation: Set<Endpoint> = setOf(
            Endpoint("/todos", GET),
            Endpoint("/todos", POST),
            Endpoint("/todos", PUT),
            Endpoint("/todos", PATCH),
            Endpoint("/todos", DELETE),
            Endpoint("/todos", HEAD),
            Endpoint("/todos", OPTIONS),
            Endpoint("/todos", TRACE),
            Endpoint("/tags", GET),
            Endpoint("/tags", POST),
            Endpoint("/tags", DELETE),
            Endpoint("/tags", HEAD),
            Endpoint("/tags", OPTIONS),
        )

        // when
        val specification = WadlConverter(file)

        // then
        specification.conversionResult mustSatisfy {
            it containsExactly implementation
        }
    }
}