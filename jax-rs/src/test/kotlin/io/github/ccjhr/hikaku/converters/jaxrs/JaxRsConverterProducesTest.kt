package de.codecentric.hikaku.converters.jaxrs

import io.github.ccjhr.hikaku.endpoints.Endpoint
import io.github.ccjhr.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.hikaku.converters.jaxrs.JaxRsConverter
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test

class JaxRsConverterProducesTest {

    @Test
    fun `single media type defined on class`() {
        // given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf(
                    "application/json",
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.produces.singlemediatypeonclass").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `single media type defined on function`() {
        // given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf(
                    "application/json",
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.produces.singlemediatypeonfunction").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `single media type without return type`() {
        // given
        val specification = setOf(
            Endpoint("/todos", GET),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.produces.singlemediatypewithoutreturntype").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `return type, but no annotation`() {
        // given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf(
                    "*/*",
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.produces.noannotation").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `multiple media type defined on class`() {
        // given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf(
                    "application/json",
                    "application/xml",
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.produces.multiplemediatypesonclass").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `multiple media type defined on function`() {
        // given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf(
                    "application/json",
                    "application/xml",
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.produces.multiplemediatypesonfunction").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `function declaration overwrites class declaration`() {
        // given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                produces = setOf(
                    "application/json",
                    "text/plain",
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.produces.functiondeclarationoverwritesclassdeclaration").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }
}