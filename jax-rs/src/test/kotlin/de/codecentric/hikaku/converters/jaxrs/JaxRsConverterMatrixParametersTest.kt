package de.codecentric.hikaku.converters.jaxrs

import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.hikaku.endpoints.MatrixParameter
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test

class JaxRsConverterMatrixParametersTest {

    @Test
    fun `matrix parameter on function`() {
        //given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                matrixParameters = setOf(
                    MatrixParameter("tag"),
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.matrixparameters.onfunction").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }
}