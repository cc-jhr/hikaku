package de.codecentric.hikaku.converters.raml

import de.codecentric.hikaku.converters.EndpointConverterException
import io.github.ccjhr.mustSatisfy
import io.github.ccjhr.string.contains
import io.github.ccjhr.throwable.expectsException
import io.github.ccjhr.throwable.hasMessage
import org.junit.jupiter.api.Nested
import java.io.File
import kotlin.io.path.toPath
import kotlin.test.Test

class RamlConverterInvalidInputTest {

    @Nested
    inner class FileObjectTests {

        @Test
        fun `empty file returns an empty list`() {
            // given
            val file = File(this::class.java.classLoader.getResource("invalid_input/empty_file.raml").toURI())

            // when
            val result = expectsException<EndpointConverterException> {
                RamlConverter(file).conversionResult
            }

            // then
            result mustSatisfy {
                it hasMessage "Empty document."
            }
        }

        @Test
        fun `file consisting solely of whitespaces returns an empty list`() {
            // given
            val file =
                File(this::class.java.classLoader.getResource("invalid_input/whitespaces_only_file.raml").toURI())

            // when
            val result = expectsException<EndpointConverterException> {
                RamlConverter(file).conversionResult
            }

            // then
            result mustSatisfy {
                it hasMessage "Invalid header declaration "
            }
        }

        @Test
        fun `invalid RAML version`() {
            // given
            val file = File(this::class.java.classLoader.getResource("invalid_input/invalid_raml_version.raml").toURI())

            // when
            val result = expectsException<EndpointConverterException> {
                RamlConverter(file).conversionResult
            }

            // then
            result mustSatisfy {
                it hasMessage "Unsupported RAML version"
            }
        }

        @Test
        fun `file containing syntax error throws SpecificationParserException`() {
            // given
            val file = File(this::class.java.classLoader.getResource("invalid_input/syntax_error.raml").toURI())
            val converter = RamlConverter(file)

            // when
            val result = expectsException<EndpointConverterException> {
                converter.conversionResult
            }

            // then
            result.message mustSatisfy {
                it contains "[line=4, col=3]"
            }
        }
    }

    @Nested
    inner class PathObjectTests {

        @Test
        fun `empty file returns an empty list`() {
            // given
            val file = this::class.java.classLoader.getResource("invalid_input/empty_file.raml").toURI().toPath()

            // when
            val result = expectsException<EndpointConverterException> {
                RamlConverter(file).conversionResult
            }

            // then
            result mustSatisfy {
                it hasMessage "Empty document."
            }
        }

        @Test
        fun `file consisting solely of whitespaces returns an empty list`() {
            // given
            val file = this::class.java.classLoader.getResource("invalid_input/whitespaces_only_file.raml").toURI().toPath()

            // when
            val result = expectsException<EndpointConverterException> {
                RamlConverter(file).conversionResult
            }

            // then
            result mustSatisfy {
                it hasMessage "Invalid header declaration "
            }
        }

        @Test
        fun `invalid RAML version`() {
            // given
            val file = this::class.java.classLoader.getResource("invalid_input/invalid_raml_version.raml").toURI().toPath()

            // when
            val result = expectsException<EndpointConverterException> {
                RamlConverter(file).conversionResult
            }

            // then
            result mustSatisfy {
                it hasMessage "Unsupported RAML version"
            }
        }

        @Test
        fun `file containing syntax error`() {
            // given
            val file = this::class.java.classLoader.getResource("invalid_input/syntax_error.raml").toURI().toPath()
            val converter = RamlConverter(file)

            // when
            val result = expectsException<EndpointConverterException> {
                converter.conversionResult
            }

            // then
            result.message mustSatisfy {
                it contains "[line=4, col=3]"
            }
        }
    }
}