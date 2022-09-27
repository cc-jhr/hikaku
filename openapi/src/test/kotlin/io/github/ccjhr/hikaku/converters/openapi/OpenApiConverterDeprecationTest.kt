package io.github.ccjhr.hikaku.converters.openapi

import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test
import java.nio.file.Paths

class OpenApiConverterDeprecationTest {

    @Test
    fun `no deprecation`() {
        //given
        val file = Paths.get(this::class.java.classLoader.getResource("deprecation/deprecation_none.yaml").toURI())
        val implementation = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf("application/json"),
                deprecated = false,
            )
        )

        //when
        val specification = OpenApiConverter(file).conversionResult

        //then
        specification mustSatisfy {
            it containsExactly implementation
        }
    }

    @Test
    fun `deprecated operation`() {
        //given
        val file = Paths.get(this::class.java.classLoader.getResource("deprecation/deprecation_operation.yaml").toURI())
        val implementation = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf("application/json"),
                deprecated = true,
            )
        )

        //when
        val specification = OpenApiConverter(file).conversionResult

        //then
        specification mustSatisfy {
            it containsExactly implementation
        }
    }
}