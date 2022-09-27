package de.codecentric.hikaku.converters.jaxrs

import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.hikaku.endpoints.QueryParameter
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.converters.jaxrs.JaxRsConverter
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test

class JaxRsConverterQueryParameterTest {

    @Test
    fun `query parameter on function`() {
        //given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                queryParameters = setOf(
                    QueryParameter("filter"),
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.queryparameters.onfunction").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }
}