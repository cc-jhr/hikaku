package io.github.ccjhr.hikaku.converters.jaxrs

import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HeaderParameter
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test

class JaxRsConverterHeaderParameterTest {

    @Test
    fun `header parameter on function`() {
        //given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                headerParameters = setOf(
                    HeaderParameter("allow-cache"),
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.headerparameters.onfunction").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }
}