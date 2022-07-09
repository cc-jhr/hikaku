package de.codecentric.hikaku.converters.micronaut

import de.codecentric.hikaku.converters.EndpointConverterException
import io.github.ccjhr.mustSatisfy
import io.github.ccjhr.throwable.hasMessage
import kotlin.test.Test
import kotlin.test.assertFailsWith

class MicronautConverterPackageDefinitionTest {

    @Test
    fun `invoking converter with empty string leads to EndpointConverterException`() {
        // when
        val result = assertFailsWith<EndpointConverterException> {
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
        val result = assertFailsWith<EndpointConverterException> {
            MicronautConverter("     ").conversionResult
        }

        // then
        result mustSatisfy {
            it hasMessage "Package name must not be blank."
        }
    }
}