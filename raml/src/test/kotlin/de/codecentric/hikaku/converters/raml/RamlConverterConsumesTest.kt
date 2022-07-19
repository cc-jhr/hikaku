package de.codecentric.hikaku.converters.raml

import de.codecentric.hikaku.converters.EndpointConverterException
import de.codecentric.hikaku.endpoints.Endpoint
import de.codecentric.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import io.github.ccjhr.string.contains
import io.github.ccjhr.throwable.expectsException
import io.github.ccjhr.throwable.hasMessage
import kotlin.io.path.toPath
import kotlin.test.Test

class RamlConverterConsumesTest {

    @Test
    fun `no media type`() {
        // given
        val file = this::class.java.classLoader.getResource("consumes/no_media_type.raml").toURI().toPath()

        // when
        val result = expectsException<EndpointConverterException> {
            RamlConverter(file).conversionResult
        }

        // then
        result.message mustSatisfy {
            it contains "[line=9, col=7]"
        }
    }

    @Test
    fun `single default media type`() {
        // given
        val file = this::class.java.classLoader.getResource("consumes/single_default_media_type.raml").toURI().toPath()

        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                consumes = setOf(
                    "application/json",
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
    fun `multiple default media types`() {
        // given
        val file = this::class.java.classLoader.getResource("consumes/multiple_default_media_types.raml").toURI().toPath()

        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                consumes = setOf(
                    "application/json",
                    "application/xml",
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
    fun `single method declaration`() {
        // given
        val file = this::class.java.classLoader.getResource("consumes/single_method_declaration.raml").toURI().toPath()

        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                consumes = setOf(
                    "text/plain",
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
    fun `multiple method declarations`() {
        // given
        val file = this::class.java.classLoader.getResource("consumes/multiple_method_declarations.raml").toURI().toPath()

        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                consumes = setOf(
                    "application/json",
                    "application/xml",
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
    fun `method declaration overwrites default`() {
        // given
        val file = this::class.java.classLoader.getResource("consumes/method_declaration_overwrites_default.raml").toURI().toPath()

        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                consumes = setOf(
                    "text/plain",
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