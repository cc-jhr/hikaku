package io.github.ccjhr.hikaku.converters.jaxrs

import io.github.ccjhr.hikaku.converters.EndpointConverterException
import io.github.ccjhr.mustSatisfy
import io.github.ccjhr.throwable.expectsException
import io.github.ccjhr.throwable.hasMessage
import kotlin.test.Test

class JaxRsConverterPackageDefinitionTest {

    @Test
    fun `invoking converter with empty string leads to EndpointConverterException`() {
        // when
        val result = expectsException<EndpointConverterException> {
            JaxRsConverter("").conversionResult
        }

        // then
        result mustSatisfy {
            it hasMessage "Package name must not be blank."
        }
    }

    @Test
    fun `invoking converter with blank string leads to EndpointConverterException`() {
        // when
        val result = expectsException<EndpointConverterException> {
            JaxRsConverter("     ").conversionResult
        }

        // then
        result mustSatisfy {
            it hasMessage "Package name must not be blank."
        }
    }
}