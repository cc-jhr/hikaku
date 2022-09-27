package de.codecentric.hikaku.converters.raml

import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.*
import io.github.ccjhr.boolean.`is`
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.io.path.toPath
import kotlin.test.Test

class RamlConverterHttpMethodTest {

    @Test
    fun `extract all supported http methods`() {
        // given
        val file = this::class.java.classLoader.getResource("http_method/http_methods.raml").toURI().toPath()

        val specification = setOf(
            Endpoint("/todos", GET),
            Endpoint("/todos", POST),
            Endpoint("/todos", PUT),
            Endpoint("/todos", PATCH),
            Endpoint("/todos", DELETE),
            Endpoint("/todos", HEAD),
            Endpoint("/todos", OPTIONS),
        )

        // when
        val implementation = RamlConverter(file).conversionResult

        // then
        implementation mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `path without http methods does not create an endpoint`() {
        // given
        val file = this::class.java.classLoader.getResource("http_method/path_without_http_method.raml").toURI().toPath()

        // when
        val implementation = RamlConverter(file).conversionResult

        // then
        implementation.isEmpty() mustSatisfy {
            it `is` true // TODO: could be changed to `is` Empty when KT-47475 gets fixed
        }
    }
}