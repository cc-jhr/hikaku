package io.github.ccjhr.hikaku.converters.raml

import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.hikaku.endpoints.HttpMethod.POST
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.io.path.toPath
import kotlin.test.Test

class RamlConverterPathTest {

    @Test
    fun `simple path`() {
        // given
        val file = this::class.java.classLoader.getResource("path/simple_path.raml").toURI().toPath()

        val specification = setOf(
            Endpoint("/todos", GET),
        )

        // when
        val implementation = RamlConverter(file).conversionResult

        // then
        implementation mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `extract nested paths`() {
        // given
        val file = this::class.java.classLoader.getResource("path/nested_path.raml").toURI().toPath()

        val specification = setOf(
            Endpoint("/todo", POST),
            Endpoint("/todo/list", GET),
        )

        // when
        val implementation = RamlConverter(file).conversionResult

        // then
        implementation mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `extract nested paths defined in a single entry`() {
        // given
        val file = this::class.java.classLoader.getResource("path/nested_path_single_entry.raml").toURI().toPath()

        val specification = setOf(
            Endpoint("/todo/list", POST),
            Endpoint("/todo/list", GET),
        )

        // when
        val implementation = RamlConverter(file).conversionResult

        // then
        implementation mustSatisfy {
            it containsExactly specification
        }
    }
}