package io.github.ccjhr.hikaku.converters.wadl

import io.github.ccjhr.any.isEqualTo
import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.mustSatisfy
import java.nio.file.Paths
import kotlin.test.Test

class WadlConverterConsumesTest {

    @Test
    fun `check that media type information for the response are extracted correctly`() {
        // given
        val file = Paths.get(this::class.java.classLoader.getResource("consumes/consumes_three_media_types.wadl").toURI())
        val implementation: Set<Endpoint> = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                consumes = setOf(
                    "application/json",
                    "application/xml",
                    "text/plain",
                )
            )
        )

        // when
        val specification = WadlConverter(file).conversionResult

        // then
        specification mustSatisfy {
            it isEqualTo implementation
        }
    }

    @Test
    fun `check that no media type information result in empty consumes list`() {
        // given
        val file = Paths.get(this::class.java.classLoader.getResource("consumes/consumes_no_media_types.wadl").toURI())
        val implementation: Set<Endpoint> = setOf(
            Endpoint("/todos", GET),
        )

        // when
        val specification = WadlConverter(file).conversionResult

        // then
        specification mustSatisfy {
            it isEqualTo implementation
        }
    }

    @Test
    fun `check that media types are not extracted from response info`() {
        // given
        val file = Paths.get(this::class.java.classLoader.getResource("consumes/consumes_media_types_not_taken_from_produces.wadl").toURI())
        val implementation: Set<Endpoint> = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf(
                    "application/json",
                    "application/xml",
                    "text/plain"
                )
            ),
        )

        // when
        val specification = WadlConverter(file).conversionResult

        // then
        specification mustSatisfy {
            it isEqualTo implementation
        }
    }
}