package io.github.ccjhr.hikaku.converters.raml

import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.io.path.toPath
import kotlin.test.Test

class RamlConverterDeprecationTest {

    @Test
    fun `no deprecations`() {
        // given
        val file = this::class.java.classLoader.getResource("deprecation/none.raml").toURI().toPath()

        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf("text/plain"),
                deprecated = false,
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
    fun `deprecated resource`() {
        // given
        val file = this::class.java.classLoader.getResource("deprecation/on_resource.raml").toURI().toPath()

        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf("text/plain"),
                deprecated = true,
            )
        )

        // when
        val implementation = RamlConverter(file).conversionResult

        // then
        implementation mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `deprecated method`() {
        // given
        val file = this::class.java.classLoader.getResource("deprecation/on_method.raml").toURI().toPath()

        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf("text/plain"),
                deprecated = true,
            )
        )

        // when
        val implementation = RamlConverter(file).conversionResult

        // then
        implementation mustSatisfy {
            it containsExactly specification
        }
    }
}