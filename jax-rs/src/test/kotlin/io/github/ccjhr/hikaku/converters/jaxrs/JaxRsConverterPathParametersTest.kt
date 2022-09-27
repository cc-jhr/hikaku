package io.github.ccjhr.hikaku.converters.jaxrs

import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.hikaku.endpoints.PathParameter
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.converters.jaxrs.JaxRsConverter
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test

class JaxRsConverterPathParametersTest {

    @Test
    fun `no path parameter`() {
        //given
        val specification = setOf(
            Endpoint("/todos/{id}", GET),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.pathparameters.nopathparameter").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `path parameter on function`() {
        //given
        val specification = setOf(
            Endpoint(
                path = "/todos/{id}",
                httpMethod = GET,
                pathParameters = setOf(
                    PathParameter("id"),
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.pathparameters.onfunction").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }
}