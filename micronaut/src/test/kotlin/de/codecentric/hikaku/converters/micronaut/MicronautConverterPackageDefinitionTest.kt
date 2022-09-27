package de.codecentric.hikaku.converters.micronaut

import io.github.ccjhr.hikaku.converters.EndpointConverterException
import io.github.ccjhr.mustSatisfy
import io.github.ccjhr.throwable.expectsException
import io.github.ccjhr.throwable.hasMessage
import kotlin.test.Test

class MicronautConverterPackageDefinitionTest {

    @Test
    fun `invoking converter with empty string leads to EndpointConverterException`() {
        // when
        val result = expectsException<EndpointConverterException> {
            MicronautConverter("").conversionResult
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
            MicronautConverter("     ").conversionResult
        }

        // then
        result mustSatisfy {
            it hasMessage "Package name must not be blank."
        }
    }
}