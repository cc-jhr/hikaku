package de.codecentric.hikaku.converters.jaxrs

import de.codecentric.hikaku.endpoints.Endpoint
import de.codecentric.hikaku.endpoints.HttpMethod.*
import io.github.ccjhr.collection.CollectionAssertionAdjective.Empty
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.collection.`is`
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test

class JaxRsConverterHttpMethodsTest {

    @Test
    fun `extract all available http methods`() {
        //given
        val specification = setOf(
            Endpoint("/todos", GET),
            Endpoint("/todos", DELETE),
            Endpoint("/todos", POST),
            Endpoint("/todos", PUT),
            Endpoint("/todos", PATCH),
            Endpoint("/todos", OPTIONS),
            Endpoint("/todos", HEAD),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.httpmethod.allmethods").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `resource class without http method annotation`() {
        //when
        val result = JaxRsConverter("test.jaxrs.httpmethod.noannotation").conversionResult

        //then
        result mustSatisfy {
            it `is` Empty
        }
    }
}