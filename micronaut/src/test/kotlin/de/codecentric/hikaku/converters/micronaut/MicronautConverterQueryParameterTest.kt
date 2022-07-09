package de.codecentric.hikaku.converters.micronaut

import de.codecentric.hikaku.endpoints.Endpoint
import de.codecentric.hikaku.endpoints.HttpMethod.GET
import de.codecentric.hikaku.endpoints.QueryParameter
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test

class MicronautConverterQueryParameterTest {

    @Test
    fun `query parameter required`() {
        //given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                queryParameters = setOf(
                    QueryParameter("filter", true),
                ),
            ),
        )

        //when
        val result = MicronautConverter("test.micronaut.queryparameters.required.annotation").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `query parameter optional, because a default value exists`() {
        //given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                queryParameters = setOf(
                    QueryParameter("filter", false),
                ),
            ),
        )

        //when
        val result = MicronautConverter("test.micronaut.queryparameters.optional").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `query is required and defined without annotation, because no matching template exists in url`() {
        //given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                queryParameters = setOf(
                    QueryParameter("filter", false),
                ),
            ),
        )

        //when
        val result = MicronautConverter("test.micronaut.queryparameters.required.withoutannotation").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }
}