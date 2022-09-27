package io.github.ccjhr.hikaku.converters.micronaut

import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test

class MicronautConverterDeprecationTest {

    @Test
    fun `no deprecation`() {
        // given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                deprecated = false,
            ),
        )

        //when
        val result = MicronautConverter("test.micronaut.deprecation.none").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `deprecated class`() {
        // given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                deprecated = true,
            ),
        )

        //when
        val result = MicronautConverter("test.micronaut.deprecation.onclass").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `deprecated function`() {
        // given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                deprecated = true,
            ),
        )

        //when
        val result = MicronautConverter("test.micronaut.deprecation.onfunction").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }
}