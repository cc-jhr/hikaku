package de.codecentric.hikaku.converters.wadl

import de.codecentric.hikaku.converters.EndpointConverterException
import io.github.ccjhr.mustSatisfy
import io.github.ccjhr.throwable.expectsException
import io.github.ccjhr.throwable.hasMessage
import org.junit.jupiter.api.Nested
import java.io.File
import java.nio.file.Paths
import kotlin.test.Test

class WadlConverterInvalidInputTest {

    @Nested
    inner class PathObjectTests {

        @Test
        fun `empty file returns an empty list`() {
            // given
            val file = Paths.get(this::class.java.classLoader.getResource("invalid_input/empty_file.wadl").toURI())

            // when
            val result = expectsException<EndpointConverterException> {
                WadlConverter(file).conversionResult
            }
            
            // then
            result mustSatisfy {
                it hasMessage "Given WADL is blank."
            }
        }

        @Test
        fun `file consisting solely of whitespaces returns an empty list`() {
            // given
            val file = Paths.get(this::class.java.classLoader.getResource("invalid_input/whitespaces_only_file.wadl").toURI())

            // when
            val result = expectsException<EndpointConverterException> {
                WadlConverter(file).conversionResult
            }

            // then
            result mustSatisfy {
                it hasMessage "Given WADL is blank."
            }
        }

        @Test
        fun `file containing syntax error`() {
            // given
            val file = Paths.get(this::class.java.classLoader.getResource("invalid_input/syntax_error.wadl").toURI())
            val converter = WadlConverter(file)

            // when
            expectsException<EndpointConverterException> {
                converter.conversionResult
            }
        }
    }

    @Nested
    inner class FileObjectTests {

        @Test
        fun `empty file returns an empty list`() {
            // given
            val file = File(this::class.java.classLoader.getResource("invalid_input/empty_file.wadl").toURI())

            // when
            val result = expectsException<EndpointConverterException> {
                WadlConverter(file).conversionResult
            }

            // then
            result mustSatisfy {
                it hasMessage "Given WADL is blank."
            }
        }

        @Test
        fun `file consisting solely of whitespaces returns an empty list`() {
            // given
            val file = File(this::class.java.classLoader.getResource("invalid_input/whitespaces_only_file.wadl").toURI())

            // when
            val result = expectsException<EndpointConverterException> {
                WadlConverter(file).conversionResult
            }


            // then
            result mustSatisfy {
                it hasMessage "Given WADL is blank."
            }
        }

        @Test
        fun `file containing syntax error`() {
            // given
            val file = File(this::class.java.classLoader.getResource("invalid_input/syntax_error.wadl").toURI())
            val converter = WadlConverter(file)

            // when
            expectsException<EndpointConverterException> {
                converter.conversionResult
            }
        }
    }
}