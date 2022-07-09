package de.codecentric.hikaku.converters.jaxrs

import de.codecentric.hikaku.endpoints.Endpoint
import de.codecentric.hikaku.endpoints.HttpMethod.GET
import io.github.ccjhr.collection.CollectionAssertionAdjective
import io.github.ccjhr.collection.CollectionAssertionAdjective.Empty
import io.github.ccjhr.collection.containsExactly
import io.github.ccjhr.collection.`is`
import io.github.ccjhr.mustSatisfy
import kotlin.test.Test

class JaxRsConverterPathTests {

    @Test
    fun `simple path`() {
        // given
        val specification = setOf(
            Endpoint("/todos", GET),
        )

        //when
        val result = JaxRsConverter("test.jaxrs.path.simplepath").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `simple path without leading slash`() {
        // given
        val specification = setOf(
            Endpoint("/todos", GET)
        )

        //when
        val result = JaxRsConverter("test.jaxrs.path.simplepathwithoutleadingslash").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `nested path`() {
        // given
        val specification = setOf(
            Endpoint("/todo/list", GET)
        )

        //when
        val result = JaxRsConverter("test.jaxrs.path.nestedpath").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `nested path without leading slash`() {
        // given
        val specification = setOf(
            Endpoint("/todo/list", GET)
        )

        //when
        val result = JaxRsConverter("test.jaxrs.path.nestedpathwithoutleadingslash").conversionResult

        //then
        result mustSatisfy {
            it containsExactly specification
        }
    }

    @Test
    fun `resource class is not detected, if there is no Path annotation on class level`() {
        //when
        val result = JaxRsConverter("test.jaxrs.path.nopathonclass").conversionResult

        //then
        result mustSatisfy {
            it `is` Empty
        }
    }
}