package io.github.ccjhr.hikaku.converters.micronaut

import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HeaderParameter
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test

class MicronautConverterHeaderParameterTest {

    @Test
    fun `optional header parameter`() {
        //given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                headerParameters = setOf(
                    HeaderParameter("allow-cache", false),
                ),
            ),
        )

        //when
        val result = MicronautConverter("test.micronaut.headerparameters.optional").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `required header parameter`() {
        //given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                headerParameters = setOf(
                    HeaderParameter("allow-cache", true),
                ),
            ),
        )

        //when
        val result = MicronautConverter("test.micronaut.headerparameters.required").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }
}