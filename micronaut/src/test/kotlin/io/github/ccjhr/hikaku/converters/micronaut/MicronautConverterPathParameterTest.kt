package io.github.ccjhr.hikaku.converters.micronaut

import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.hikaku.endpoints.PathParameter
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test

class MicronautConverterPathParameterTest {

    @Test
    fun `path parameter defined by variable name`() {
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
        val result = MicronautConverter("test.micronaut.pathparameters.variable").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `path parameter defined by annotation using 'value'`() {
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
        val result = MicronautConverter("test.micronaut.pathparameters.annotation.value").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `path parameter defined by annotation using 'name'`() {
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
        val result = MicronautConverter("test.micronaut.pathparameters.annotation.name").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }
}