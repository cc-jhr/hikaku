package de.codecentric.hikaku.converters.jaxrs

import de.codecentric.hikaku.endpoints.Endpoint
import de.codecentric.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test

class JaxRsConverterConsumesTest {

    @Test
    fun `single media type defined on class`() {
        // given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                consumes = setOf(
                    "application/json",
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.consumes.singlemediatypeonclass").conversionResult

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
                consumes = setOf(
                    "application/json",
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.consumes.singlemediatypeonfunction").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `single media type without request body`() {
        // given
        val specification = setOf(
            Endpoint("/todos", GET),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.consumes.singlemediatypewithoutrequestbody").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `request body, but no annotation`() {
        // given
        val specification = setOf(
            Endpoint(
                path = "/todos",
                httpMethod = GET,
                consumes = setOf(
                    "*/*",
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.consumes.noannotation").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `no request body, but other annotated parameter`() {
        // given
        val specification = setOf(
            Endpoint("/todos", GET),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.consumes.singlemediatypewithoutrequestbodybutotherannotatedparameter").conversionResult

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
                consumes = setOf(
                    "application/json",
                    "application/xml",
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.consumes.multiplemediatypesonclass").conversionResult

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
                consumes = setOf(
                    "application/json",
                    "application/xml",
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.consumes.multiplemediatypesonfunction").conversionResult

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
                consumes = setOf(
                    "application/json",
                    "text/plain",
                ),
            ),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.consumes.functiondeclarationoverwritesclassdeclaration").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }
}